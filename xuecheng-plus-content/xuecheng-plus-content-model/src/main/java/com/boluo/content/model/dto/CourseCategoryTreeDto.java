package com.boluo.content.model.dto;

import com.boluo.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author kirit
 * @version 1.0
 * @description: TODO
 * @date 2023/5/30 21:55
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    //子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;

}
