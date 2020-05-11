package com.xinghuo.ams.mq.service.impl.transactionListener;

import com.xinghuo.ams.mq.service.MqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * @ClassName TransactionListener
 * @Description TODO
 * @Author weilibin
 * @Date 2019-08-12 14:09
 * @Version 1.0.0
 */

@Component
public class TransactionListener {

    private final Logger log = LoggerFactory.getLogger(TransactionListener.class);
    @Resource
    private MqService mqService;

    @TransactionalEventListener
    public void afterVendorChange(final Object vendorQuery) {
        // 业务操作，确保事务提交后发送MQ
    }

}
