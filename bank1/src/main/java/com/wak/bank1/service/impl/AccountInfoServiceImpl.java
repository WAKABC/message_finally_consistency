package com.wak.bank1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wak.bank1.entities.AccountChangeEvent;
import com.wak.bank1.mapper.AccountInfoMapper;
import com.wak.bank1.mapper.DuplicateMapper;
import com.wak.bank1.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wuankang
 * @date 2023/11/2 17:40
 * @Description TODO
 * @Version 1.0
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private DuplicateMapper duplicateMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    /**
     * send half model
     *
     * @param accountChangeEvent
     */
    @Override
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountChange", accountChangeEvent);
        String jsonObjectString = jsonObject.toString();
        //封装消息体
        Message<String> message = MessageBuilder.withPayload(jsonObjectString).build();
        //发送消息
        rocketMQTemplate.sendMessageInTransaction("topic_txmsg", message, null);
        log.info("RocketMQ send half model success, msg:{}", jsonObjectString);
    }

    /**
     * 执行本地事务
     *
     * @param accountChangeEvent 账户变更事件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {
        //执行插入数据
        String txNo = accountChangeEvent.getTxNo();
        //幂等判断
        if (duplicateMapper.countByTxno(txNo) > 0) return;
        //扣减金额
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount());
        //插入表de—_duplicate
        duplicateMapper.addDuplicate(txNo);
        log.info("RocketMQ doUpdateAccountBalance success, txNo:{}", txNo);
    }
}
