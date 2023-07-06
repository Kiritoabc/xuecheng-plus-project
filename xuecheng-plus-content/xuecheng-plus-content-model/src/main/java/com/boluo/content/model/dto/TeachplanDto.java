package com.boluo.content.model.dto;

import com.boluo.content.model.po.Teachplan;
import com.boluo.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeachplanDto extends Teachplan {

   //课程计划关联的媒资信息
   TeachplanMedia teachplanMedia;

    //子目录
   List<TeachplanDto> teachPlanTreeNodes;
}