package com.boluo.content.service;

import com.boluo.base.model.PageParams;
import com.boluo.base.model.PageResult;
import com.boluo.content.model.dto.AddCourseDto;
import com.boluo.content.model.dto.CourseBaseInfoDto;
import com.boluo.content.model.dto.QueryCourseParamsDto;
import com.boluo.content.model.po.CourseBase;

public interface CourseBaseInfoService {
    /**
     * 课程分页查询
     * @param pageParams 分页查询参数
     * @param courseParamsDto 查询条件
     * @return 查询结果
     */
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 课程详细信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
