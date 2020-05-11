package com.xinghuo.ams.mq.rabbit.send;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

public class MqSendService {

    private final static Logger log = LoggerFactory.getLogger("mqAppender");

    private AmqpTemplate amqpTemplate;

    /**发送广播消息**/
    public void send(String exchange, Object data) {
        log.info("广播消息发送开始-exchange:{},消息体:{}",exchange, JSON.toJSONString(data));
        amqpTemplate.convertAndSend(exchange, null, data);
        log.info("广播消息发送完毕-exchange:{}",exchange);
    }

    /**
     * 发送点对点消息
     * @param queue 消息指定的对应名称
     */
    public void send(String exchange,String queue, Object data) {
        log.info("点对点消息发送开始-exchange:{},queue:{},消息体:{}",exchange, queue, JSON.toJSONString(data));
        amqpTemplate.convertAndSend(exchange, queue, data);
        log.info("点对点消息发送完毕-exchange:{},queue:{},",exchange, queue);
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

}