package com.xinghuo.ams.mq.rabbit.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;

public class FastJsonMessageConverter extends AbstractMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(FastJsonMessageConverter.class);

    public static final String DEFAULT_CHARSET = "UTF-8";

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        String jsonStr = JSON.toJSONString(object);
        byte[] bytes = null;
        try {
            bytes = jsonStr.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(DEFAULT_CHARSET);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message msg) throws MessageConversionException {
        return new Message(msg.getBody(),msg.getMessageProperties());
    }
}
