package com.boluo.media.api;

import com.boluo.base.model.PageParams;
import com.boluo.base.model.PageResult;
import com.boluo.media.model.dto.QueryMediaParamsDto;
import com.boluo.media.model.dto.UploadFileParamsDto;
import com.boluo.media.model.dto.UploadFileResultDto;
import com.boluo.media.model.po.MediaFiles;
import com.boluo.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.boluo.base.constant.MediaConstant.MEDIA_FILE_TYPE_PIC;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理接口
 * @date 2022/9/6 11:29
 */
@Api(value = "媒资文件管理接口", tags = "媒资文件管理接口")
@RestController
public class MediaFilesController {


    @Autowired
    MediaFileService mediaFileService;


    @ApiOperation("媒资列表查询接口")
    @PostMapping("/files")
    public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto) {
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiles(companyId, pageParams, queryMediaParamsDto);
    }

    @ApiOperation("上传图片")
    @PostMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResultDto upload(@RequestParam("filedata")MultipartFile upload) throws IOException {
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        // 原始文件名称
        uploadFileParamsDto.setFilename(upload.getOriginalFilename());
        // 文件大小
        uploadFileParamsDto.setFileSize(upload.getSize());
        // 文件类型
        uploadFileParamsDto.setFileType(MEDIA_FILE_TYPE_PIC);

        // 拿到文件路径
        File tempFile = File.createTempFile("minio", ".temp");
        upload.transferTo(tempFile);
        // 文件路径取出
        String localFilePath = tempFile.getAbsolutePath();

        return mediaFileService.uploadFile(companyId, uploadFileParamsDto, localFilePath);
    }

}
