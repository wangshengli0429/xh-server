package com.xinghuo.ams.mq.service.impl;

import com.xinghuo.ams.mq.rabbit.send.MqSendService;
import com.xinghuo.ams.mq.service.MqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqServiceImpl implements MqService {

    private final Logger log = LoggerFactory.getLogger(MqServiceImpl.class);

    @Resource
    private MqSendService mqSendService;
}
