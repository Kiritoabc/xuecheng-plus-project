package com.boluo.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boluo.content.mapper.MqMessageMapper;
import com.boluo.content.model.po.MqMessage;
import com.boluo.content.service.MqMessageService;
import org.springframework.stereotype.Service;

/**
* @author kirit
* @description 针对表【mq_message】的数据库操作Service实现
* @createDate 2023-05-27 18:42:19
*/
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage>
    implements MqMessageService{

}




