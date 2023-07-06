package com.boluo.content.api;

import com.boluo.content.mapper.TeachplanMapper;
import com.boluo.content.model.dto.SaveTeachplanDto;
import com.boluo.content.model.dto.TeachplanDto;
import com.boluo.content.service.TeachplanService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kirito
 * @version 1.0
 * @description: TODO
 * @date 2023/7/5 23:08
 */
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachPlanController {



    @Autowired
    private TeachplanService teachplanService;


    /**
     *  查询
     * @param courseId
     * @return
     */
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){

        return teachplanService.findTeachPlanTree(courseId);
    }

    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto dto) {
        teachplanService.saveTeachplan(dto);
    }

}
