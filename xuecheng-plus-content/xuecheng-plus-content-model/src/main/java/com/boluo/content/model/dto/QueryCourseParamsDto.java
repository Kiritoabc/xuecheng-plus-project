package com.boluo.content.model.dto;

import lombok.Data;

/**
 * @author kirito
 * @version 1.0
 * @description: 课程查询条件dto
 * @date 2023/5/27 18:56
 */
@Data
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
