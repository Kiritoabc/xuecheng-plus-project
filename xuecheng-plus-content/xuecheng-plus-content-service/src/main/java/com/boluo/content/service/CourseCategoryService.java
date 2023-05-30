package com.boluo.content.service;

import com.boluo.content.model.dto.CourseCategoryTreeDto;
import com.boluo.content.model.po.CourseCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author kirit
* @description 针对表【course_category(课程分类)】的数据库操作Service
* @createDate 2023-05-27 18:42:19
*/
public interface CourseCategoryService extends IService<CourseCategory> {
    /**
     * 课程分类树形结构查询
     *
     * @return
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String number);
}
