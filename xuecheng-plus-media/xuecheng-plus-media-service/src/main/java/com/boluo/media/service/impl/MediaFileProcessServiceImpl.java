package com.boluo.media.service.impl;

import com.boluo.media.mapper.MediaFilesMapper;
import com.boluo.media.mapper.MediaProcessHistoryMapper;
import com.boluo.media.mapper.MediaProcessMapper;
import com.boluo.media.model.po.MediaFiles;
import com.boluo.media.model.po.MediaProcess;
import com.boluo.media.model.po.MediaProcessHistory;
import com.boluo.media.service.MediaFileProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.boluo.base.constant.MediaConstant.MEDIA_ADD_VIDEO_ERROR_STATUS_CODE;
import static com.boluo.base.constant.MediaConstant.MEDIA_ADD_VIDEO_SUCCESS_STATUS_CODE;

/**
 * 媒体文件处理服务impl
 *
 * @author spongzi
 * @date 2023/07/23
 */
@Slf4j
@Service
public class MediaFileProcessServiceImpl implements MediaFileProcessService {

    @Resource
    private MediaProcessMapper mediaProcessMapper;

    @Resource
    private MediaFilesMapper mediaFilesMapper;

    @Resource
    private MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Override

    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        return mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);
    }

    public boolean startTask(long id) {
        int result = mediaProcessMapper.startTask(id);
        return result > 0;
    }

    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        // 要更新的内容
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if (mediaProcess == null) {
            return;
        }
        // 如果任务执行失败
        if (MEDIA_ADD_VIDEO_ERROR_STATUS_CODE.equals(status)) {
            // 更新mediaProcess的状态
            mediaProcess.setStatus(MEDIA_ADD_VIDEO_ERROR_STATUS_CODE);
            // 更新失败次数
            mediaProcess.setFailCount(mediaProcess.getFailCount() + 1);
            mediaProcess.setErrormsg(errorMsg);
            mediaProcessMapper.updateById(mediaProcess);
            return;
        }

        // 如果任务执行成功
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        // 需要更新mediaFile中的url
        mediaFiles.setUrl(url);
        mediaFilesMapper.updateById(mediaFiles);
        // 更新mediaProcess表中的状态
        mediaProcess.setStatus(MEDIA_ADD_VIDEO_SUCCESS_STATUS_CODE);
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcess.setUrl(url);
        mediaProcessMapper.updateById(mediaProcess);
        // 把数据插入到没打ProcessHistory
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        // 从mediaProcess中删除数据
        mediaProcessMapper.deleteById(taskId);
    }

}
