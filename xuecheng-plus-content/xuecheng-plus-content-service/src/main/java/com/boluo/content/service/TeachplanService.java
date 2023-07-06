package com.boluo.content.service;

import com.boluo.content.model.dto.SaveTeachplanDto;
import com.boluo.content.model.dto.TeachplanDto;
import com.boluo.content.model.po.Teachplan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author kirit
* @description 针对表【teachplan(课程计划)】的数据库操作Service
* @createDate 2023-05-27 18:42:19
*/
public interface TeachplanService extends IService<Teachplan> {

    /**
     * @description TODO
     * @version 1.0
     */
    List<TeachplanDto> findTeachPlanTree(Long courseId);

    /**
     * @description 保存课程计划(新增/修改)
     * @param dto
     * @return void
     */
    void saveTeachplan(SaveTeachplanDto dto);
}
