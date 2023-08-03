package com.boluo.base.constant;

/**
 * 媒体常量
 *
 * @author spongzi
 * @date 2023/07/19
 */
public interface MediaConstant {
    /**
     * 媒体状态显示
     */
    String MEDIA_STATUS_SHOW = "1";

    /**
     * 媒体审核成功
     */
    String MEDIA_AUDIT_STATUS_SUCCESS = "002003";
    /**
     * 媒体文件类型图片
     */
    String MEDIA_FILE_TYPE_PIC = "001001";

    /**
     * 媒体文件类型视频
     */
    String MEDIA_FILE_TYPE_VIDEO = "001002";

    /**
     * 媒体文件视频标签
     */
    String MEDIA_FILE_VIDEO_TAG = "课程视频";

    /**
     * 媒体视频mime类型值
     */
    String MEDIA_VIDEO_MIME_TYPE_VALUE = "video/x-msvideo";

    /**
     * 媒体添加视频等待状态代码
     */
    String MEDIA_ADD_VIDEO_PENDING_STATUS_CODE = "1";

    /**
     * 媒体添加视频成功状态代码
     */
    String MEDIA_ADD_VIDEO_SUCCESS_STATUS_CODE = "2";

    /**
     * 媒体添加视频错误状态码
     */
    String MEDIA_ADD_VIDEO_ERROR_STATUS_CODE = "3";

    /**
     * 媒体添加视频失败违约计数
     */
    Integer MEDIA_ADD_VIDEO_FAIL_DEFAULT_COUNT = 0;

    /**
     * 媒体类型avi视频mp4结果成功
     */
    String MEDIA_VIDEO_TYPE_AVI_TO_MP4_RESULT_SUCCESS = "success";

    /**
     * 媒体mp4视频mime类型值
     */
    String MEDIA_VIDEO_MIME_TYPE_MP4_VALUE = "video/mp4";
}
