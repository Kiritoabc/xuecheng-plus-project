package com.boluo.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boluo.base.exception.XueChengPlusException;
import com.boluo.content.mapper.TeachplanMapper;
import com.boluo.content.mapper.TeachplanMediaMapper;
import com.boluo.content.model.dto.BindTeachplanMediaDto;
import com.boluo.content.model.dto.SaveTeachplanDto;
import com.boluo.content.model.dto.TeachplanDto;
import com.boluo.content.model.po.Teachplan;
import com.boluo.content.model.po.TeachplanMedia;
import com.boluo.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author kirit
* @description 针对表【teachplan(课程计划)】的数据库操作Service实现
* @createDate 2023-05-27 18:42:19
*/
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan>
    implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachPlanTree(Long courseId) {

        return teachplanMapper.selectTreeNodes(courseId);
    }

    //新增、修改
    @Override
    public void saveTeachplan(SaveTeachplanDto dto) {

        Long id = dto.getId();
        Teachplan teachplan = teachplanMapper.selectById(id);
        if(teachplan==null){
            teachplan = new Teachplan();
            BeanUtils.copyProperties(dto,teachplan);
            //找到同级课程计划的数量
            int count = getTeachplanCount(dto.getCourseId(), dto.getParentid());
            //新课程计划的值
            teachplan.setOrderby(count+1);
            teachplanMapper.insert(teachplan);
        }else{
            BeanUtils.copyProperties(dto,teachplan);
            //更新
            teachplanMapper.updateById(teachplan);
        }
    }

    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        //教学计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan == null) {
            XueChengPlusException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if (grade != 2) {
            XueChengPlusException.cast("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachplan.getCourseId();
        // 先删除原有记录, 根据课程计划的Id删除他所绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                .eq(TeachplanMedia::getTeachplanId, bindTeachplanMediaDto.getTeachplanId()));
        // 再添加原有记录
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }


    //计算机新课程计划的orderby 找到同级课程计划的数量 SELECT count(1) from teachplan where course_id=117 and parentid=268
    public int getTeachplanCount(Long courseId,Long parentId){

        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count.intValue();

    }
}




