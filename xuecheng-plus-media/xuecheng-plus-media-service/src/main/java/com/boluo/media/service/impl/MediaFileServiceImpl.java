package com.boluo.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boluo.base.exception.XueChengPlusException;
import com.boluo.base.model.PageParams;
import com.boluo.base.model.PageResult;
import com.boluo.base.model.RestResponse;
import com.boluo.media.mapper.MediaFilesMapper;
import com.boluo.media.mapper.MediaProcessMapper;
import com.boluo.media.model.dto.QueryMediaParamsDto;
import com.boluo.media.model.dto.UploadFileParamsDto;
import com.boluo.media.model.dto.UploadFileResultDto;
import com.boluo.media.model.po.MediaFiles;
import com.boluo.media.model.po.MediaProcess;
import com.boluo.media.service.MediaFileService;
import com.boluo.media.utils.MinIOUtils;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.boluo.base.constant.MediaConstant.*;
import static com.boluo.base.enumeration.ErrorEnum.*;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/9/10 8:58
 */
@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {

    @Resource
    private MediaFilesMapper mediaFilesMapper;

    @Resource
    private MediaProcessMapper mediaProcessMapper;

    @Resource
    private MinioClient minioClient;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private MinIOUtils minIOUtils;

    // 存储普通文件
    @Value("${minio.bucket.files}")
    private String bucketMediaFiles;

    // 存储视频文件
    @Value("${minio.bucket.videofiles}")
    private String bucketVideoFiles;

    @Override
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();

        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());

    }

    @Override
    @Transactional
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
        // 先得到拓展名
        String filename = uploadFileParamsDto.getFilename();
        String extension = filename.substring(filename.lastIndexOf("."));

        // 得到媒体类型
        String mimeType = minIOUtils.getMimeType(extension);
        String defaultFolderPath = minIOUtils.getDefaultFolderPath();
        String fileMd5 = minIOUtils.getFileMd5(new File(localFilePath));
        String objectName = defaultFolderPath + fileMd5 + extension;

        // 将文件上传到minIO
        boolean addMediaFilesToMinIO = minIOUtils.addMediaFilesToMinIO(localFilePath, mimeType, bucketMediaFiles, objectName);
        // 上传失败
        if (!addMediaFilesToMinIO) {
            XueChengPlusException.cast(String.valueOf(MEDIA_UPLOAD_FILE_ERROR));
        }

        // 将文件数据存到数据库中
        MediaFileServiceImpl ctxBean = applicationContext.getBean(MediaFileServiceImpl.class);
        MediaFiles mediaFiles = ctxBean.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketMediaFiles, objectName);
        if (mediaFiles == null) {
            XueChengPlusException.cast(String.valueOf(MEDIA_UPLOAD_FILE_ERROR));
        }

        // 准备返回的对象
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
        return uploadFileResultDto;
    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            return RestResponse.success(false);
        }
        // 桶
        String bucket = mediaFiles.getBucket();
        // 获取地址
        String objectName = mediaFiles.getFilePath();
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build();
        try {
            FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
            if (inputStream == null) {
                return RestResponse.success(false);
            }
        } catch (Exception e) {
            XueChengPlusException.cast(String.valueOf(MEDIA_VIDEO_NOT_FOUND));
        }
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {
        //得到分块文件目录
        String chunkFileFolderPath = minIOUtils.getChunkFileFolderPath(fileMd5);
        //得到分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunkIndex;

        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketVideoFiles)
                .object(chunkFilePath)
                .build();
        try {
            FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
            if (inputStream == null) {
                return RestResponse.success(false);
            }
        } catch (Exception e) {
            return RestResponse.success(false);
        }
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String localChunkFilePath) {
        // 分块文件的路径
        String chunkFilePath = minIOUtils.getChunkFileFolderPath(fileMd5) + chunk;
        String mimeType = minIOUtils.getMimeType(null);
        // 将分块文件上传到minio
        boolean addMediaFilesToMinIO = minIOUtils.addMediaFilesToMinIO(localChunkFilePath, mimeType, bucketVideoFiles, chunkFilePath);
        if (!addMediaFilesToMinIO) {
            return RestResponse.validfail(false, "上传分块文件失败");
        }
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> mergeChunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        // 找到文件, 调用minio接口进行合并
        String chunkFileFolderPath = minIOUtils.getChunkFileFolderPath(fileMd5);
        // 组成将分块文件路径组成 List<ComposeSource>
        List<ComposeSource> sourceObjectList = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket(bucketVideoFiles)
                        .object(chunkFileFolderPath.concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());

        //=====合并=====
        //文件名称
        String fileName = uploadFileParamsDto.getFilename();
        //文件扩展名
        String extName = fileName.substring(fileName.lastIndexOf("."));
        //合并文件路径
        String mergeFilePath = minIOUtils.getFilePathByMd5(fileMd5, extName);
        try {
            //合并文件
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(bucketVideoFiles)
                            .object(mergeFilePath)
                            .sources(sourceObjectList)
                            .build());
            log.debug("合并文件成功:{}", mergeFilePath);
        } catch (Exception e) {
            log.debug("合并文件失败,fileMd5:{},异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail(false, "合并文件失败。");
        }

        // 校验合并后和源文件是否相同, 相同视频上传才成功
        File file = minIOUtils.downloadFileFromMinIO(bucketVideoFiles, mergeFilePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // 计算合并后的md5
            String md5MergeFile = DigestUtils.md5Hex(fileInputStream);
            // 比较原始的md5值
            if (!fileMd5.equals(md5MergeFile)) {
                log.error("校验合并文件md5值不一致, 原始文件: {}, 合并文件: {}", fileMd5, md5MergeFile);
                return RestResponse.validfail(false, "文件校验失败");
            }
            // 下载完成后设置文件大小,并且设置参数返回给前端
            uploadFileParamsDto.setFileSize(file.length());
        } catch (Exception e) {
            log.debug("校验文件失败,fileMd5:{},异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail(false, "文件合并校验失败，最终上传失败。");
        }

        // 将文件信息入库
        MediaFileServiceImpl bean = applicationContext.getBean(MediaFileServiceImpl.class);
        MediaFiles mediaFiles = bean.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketVideoFiles, mergeFilePath);
        if (mediaFiles == null) {
            return RestResponse.validfail(false, "文件入库失败");
        }

        // 清理分块文件
        Boolean chunkFiles = minIOUtils.clearChunkFiles(chunkFileFolderPath, chunkTotal);
        if (!chunkFiles) {
            return RestResponse.success(false, "清理文件失败");
        }

        return RestResponse.success(true);
    }

    @Override
    public MediaFiles getFileById(String mediaId) {
        return mediaFilesMapper.selectById(mediaId);
    }


    /**
     * 媒体文件添加到数据库
     *
     * @param companyId           机构id
     * @param fileMd5             文件md5值
     * @param uploadFileParamsDto 上传文件的信息
     * @param bucket              桶
     * @param objectName          对象名称
     * @return {@link MediaFiles}
     */
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId,
                                        String fileMd5,
                                        UploadFileParamsDto uploadFileParamsDto,
                                        String bucket,
                                        String objectName) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            return mediaFiles;
        }
        mediaFiles = new MediaFiles();
        BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
        mediaFiles.setId(fileMd5);
        mediaFiles.setFileId(fileMd5);
        mediaFiles.setCompanyId(companyId);
        mediaFiles.setUrl("/" + bucket + "/" + objectName);
        mediaFiles.setBucket(bucket);
        mediaFiles.setFilePath(objectName);
        mediaFiles.setCreateDate(LocalDateTime.now());
        mediaFiles.setAuditStatus(MEDIA_AUDIT_STATUS_SUCCESS);
        mediaFiles.setStatus(MEDIA_STATUS_SHOW);
        // 插入数据库
        int insert = mediaFilesMapper.insert(mediaFiles);
        if (insert < 0) {
            log.error("保存文件信息到数据库失败,{}", mediaFiles);
            XueChengPlusException.cast(String.valueOf(MEDIA_SAVE_FILE_INFO_ERROR));
        }
        log.debug("保存文件信息到数据库成功,{}", mediaFiles);
        // 记录待处理任务
        this.addWaitingTask(mediaFiles);
        return mediaFiles;
    }

    /**
     * 添加待处理任务
     *
     * @param mediaFiles 媒体文件
     */
    private void addWaitingTask(MediaFiles mediaFiles) {
        // 通过mimeType判断文件格式, 如果是avi视频才写入待处理任务
        // 获取文件的名称
        String filename = mediaFiles.getFilename();
        // 文件拓展名
        String extension = filename.substring(filename.lastIndexOf("."));
        String mimeType = minIOUtils.getMimeType(extension);
        if (MEDIA_VIDEO_MIME_TYPE_VALUE.equals(mimeType)) {
            // 向MediaProcess插入记录
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(mediaFiles, mediaProcess);
            // 状态是待处理
            mediaProcess.setStatus(MEDIA_ADD_VIDEO_PENDING_STATUS_CODE);
            mediaProcess.setCreateDate(LocalDateTime.now());
            mediaProcess.setFailCount(MEDIA_ADD_VIDEO_FAIL_DEFAULT_COUNT);
            mediaProcessMapper.insert(mediaProcess);
        }
    }
}
