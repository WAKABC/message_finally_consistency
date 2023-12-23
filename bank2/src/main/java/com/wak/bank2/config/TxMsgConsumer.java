package com.wak.bank2.config;

import com.wak.bank2.entities.AccountChangeEvent;
import com.wak.bank2.service.AccountInfoService;
import com.wak.bank2.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/11/6 16:47
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic_txmsg", consumerGroup = "bank1_consumer_group")
public class TxMsgConsumer implements RocketMQListener<String> {

    @Resource(name = "accountInfoServiceImpl")
    private AccountInfoService accountInfoService;

    @Override
    public void onMessage(String message) {
        log.info("receiving model...");
        AccountChangeEvent accountChange = JsonUtil.getSpecifiedObj(message, "accountChange", AccountChangeEvent.class);
        if (ObjectUtils.isEmpty(accountChange)) {
            throw new RuntimeException("accountChange is null, please check it");
        }
        accountInfoService.doUpdateAccountBalance(accountChange);
        log.info("transaction model consumption success，txNo : {}", accountChange.getTxNo());
    }
}
