package com.boluo.content.api;

import com.boluo.base.exception.ValidationGroups;
import com.boluo.base.model.PageParams;
import com.boluo.base.model.PageResult;
import com.boluo.content.model.dto.AddCourseDto;
import com.boluo.content.model.dto.CourseBaseInfoDto;
import com.boluo.content.model.dto.EditCourseDto;
import com.boluo.content.model.dto.QueryCourseParamsDto;
import com.boluo.content.model.po.CourseBase;
import com.boluo.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author kirito
 * @version 1.0
 * @description: TODO
 * @date 2023/5/27 19:03
 */
@Api(value = "课程信息管理接口",tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @RequestMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams,@RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){

        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Inster.class) AddCourseDto addCourseDto){
        // 获取到用户所属的id
        Long companyId = 1232141425L;
//        int i = 1/0;
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }

    @ApiOperation("根据课程id查询")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
//        Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal1);
//        SecurityUtil.XcUser user = SecurityUtil.getUser();
//        System.out.println(user);
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto dto){

        Long companyId =1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,dto);
    }

}
