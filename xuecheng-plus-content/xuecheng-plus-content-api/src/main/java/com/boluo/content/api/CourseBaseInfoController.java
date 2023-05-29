package com.boluo.content.api;

import com.boluo.base.model.PageParams;
import com.boluo.base.model.PageResult;
import com.boluo.content.model.dto.QueryCourseParamsDto;
import com.boluo.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kirito
 * @version 1.0
 * @description: TODO
 * @date 2023/5/27 19:03
 */
@RestController
public class CourseBaseInfoController {

    @RequestMapping("course/list")
    public PageResult<CourseBase> list(PageParams pageParams,@RequestBody QueryCourseParamsDto queryCourseParamsDto){
        return null;
    }

}
