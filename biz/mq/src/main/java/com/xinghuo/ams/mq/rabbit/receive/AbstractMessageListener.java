package com.xinghuo.ams.mq.rabbit.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.StandardCharsets;

/**
 * 功能 :
 * @author : Bruce 6:32 下午 2019/9/20
 */
public abstract class AbstractMessageListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger("mqReceivable");

    abstract void onMessage(String message);

    @Override
    public void onMessage(Message message) {
        String queue = message.getMessageProperties().getConsumerQueue();
        String exchange = message.getMessageProperties().getReceivedExchange();
        String messageInfo = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("接收消息:exchange:{},queue:{},消息内容:{}",exchange,queue,messageInfo);
        // 业务操作
    }
}
