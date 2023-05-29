package com.boluo.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boluo.content.mapper.CourseTeacherMapper;
import com.boluo.content.model.po.CourseTeacher;
import com.boluo.content.service.CourseTeacherService;
import org.springframework.stereotype.Service;

/**
* @author kirit
* @description 针对表【course_teacher(课程-教师关系表)】的数据库操作Service实现
* @createDate 2023-05-27 18:42:19
*/
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher>
    implements CourseTeacherService{

}




