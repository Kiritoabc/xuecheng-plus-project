package com.boluo.content.mapper;

import com.boluo.content.model.dto.CourseCategoryTreeDto;
import com.boluo.content.model.po.CourseCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author kirit
* @description 针对表【course_category(课程分类)】的数据库操作Mapper
* @createDate 2023-05-27 18:42:19
* @Entity com.boluo.xuechengpluscontentmodel.model.po.CourseCategory
*/
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}




