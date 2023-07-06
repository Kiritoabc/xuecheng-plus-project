package com.boluo.content.mapper;

import com.boluo.content.model.dto.TeachplanDto;
import com.boluo.content.model.po.Teachplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author kirit
* @description 针对表【teachplan(课程计划)】的数据库操作Mapper
* @createDate 2023-05-27 18:42:19
* @Entity com.boluo.xuechengpluscontentmodel.model.po.Teachplan
*/
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    //查询课程计划(组成树型结构)
    public List<TeachplanDto> selectTreeNodes(Long courseId);
}




