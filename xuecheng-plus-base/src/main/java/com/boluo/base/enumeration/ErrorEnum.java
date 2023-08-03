package com.boluo.base.enumeration;

/**
 * 内容错误代码枚举
 *
 * @author spongzi
 * @date 2023/07/17
 */
public enum ErrorEnum {

    /**
     * 课程名称为空
     */
    COURSE_NAME_NULL("课程名称为空"),
    /**
     * 课程分类为空
     */
    COURSE_NAME_TYPE_NULL("课程分类为空"),
    /**
     * 课程级别空
     */
    COURSE_LEVEL_NULL("课程等级为空"),
    /**
     * 课程教育模式为空
     */
    COURSE_EDUCATION_MODEL_NULL("教育模式为空"),
    /**
     * 适应人群为空
     */
    COURSE_FOR_PEOPLE_NULL("适应人群为空"),
    /**
     * 收费规则为空
     */
    COURSE_CHARGE_MODEL_NULL("收费规则为空"),
    /**
     * 课程添加错误
     */
    COURSE_ADD_ERROR("当前课程添加异常"),
    /**
     * 课程价格错误
     */
    COURSE_PRICE_ERROR("课程设置了收费价格不能为空且必须大于0"),
    /**
     * 课程不存在
     */
    COURSE_NOT_FOUND("课程不存在"),
    /**
     * 本机构只能修改本机构的课程
     */
    COURSE_MODIFY_BY_OWN_COMPANY("本机构只能修改本机构的课程"),
    /**
     * 课程修改失败
     */
    COURSE_MODIFY_FAIL("当前课程修改失败"),
    /**
     * 课程老师添加失败
     */
    COURSE_TEACHER_ADD_FAIL("当前课程添加老师失败"),
    /**
     * 当前课程已提交, 无法进行删除操作
     */
    COURSE_AUDIT_STATUS_IS_SUBMIT("当前课程已提交, 无法进行删除操作"),
    /**
     * 课程计划信息还有子级信息，无法操作
     */
    TEACH_PLAN_HAS_CHILD("课程计划信息还有子级信息，无法操作"),
    /**
     * 当前课程计划已经不存在
     */
    TEACH_PLAN_NOT_EXIT("当前课程计划已经不存在"),
    /**
     * 当前课程计划不能移动
     */
    TEACH_PLAN_CANNOT_MOVE("已经到头啦，不能再移啦"),
    /**
     * 媒体上传文件错误
     */
    MEDIA_UPLOAD_FILE_ERROR("上传文件到文件系统失败"),
    /**
     * 媒体插入数据库错误--存在
     */
    MEDIA_INSERT_DATABASE_ERROR_EXIST("该资源已经存在"),
    /**
     * 媒体保存文件信息错误
     */
    MEDIA_SAVE_FILE_INFO_ERROR("保存文件信息失败"),
    /**
     * 媒体视频没有找到
     */
    MEDIA_VIDEO_NOT_FOUND("没有该视频文件"),

    /**
     * 媒体创建临时文件错误
     */
    MEDIA_CREATE_TEMP_FILE_ERROR("创建临时文件失败"),

    /**
     * 媒体视频类型转换失败
     */
    MEDIA_VIDEO_TYPE_TRANSFORM_FAIL("视频格式转换失败"),

    /**
     * 媒体上传mp4视频错误
     */
    MEDIA_UPLOAD_MP4_VIDEO_ERROR("上传MP4视频失败");


    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    ErrorEnum(String errMessage) {
        this.errMessage = errMessage;
    }

}
