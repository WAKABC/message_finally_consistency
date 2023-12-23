package com.wak.bank1.config;

import com.wak.bank1.entities.AccountChangeEvent;
import com.wak.bank1.mapper.DuplicateMapper;
import com.wak.bank1.service.AccountInfoService;
import com.wak.bank1.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.rmi.NoSuchObjectException;

/**
 * @author wuankang
 * @date 2023/11/4 22:30
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
@RocketMQTransactionListener
public class ProducerTxMsgListener implements RocketMQLocalTransactionListener {

    @Resource(name = "accountInfoServiceImpl")
    private AccountInfoService accountInfoService;

    @Resource
    private DuplicateMapper duplicateMapper;

    /**
     * 执行本地事务
     *
     * @param msg
     * @param arg
     * @return {@link RocketMQLocalTransactionState}
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            //get the accountChangeJsonStr from the model payload
            String msgStr = new String((byte[]) msg.getPayload());
            AccountChangeEvent accountChangeEvent = JsonUtil.getSpecifiedObj(msgStr, "accountChange", AccountChangeEvent.class);
            if (ObjectUtils.isEmpty(accountChangeEvent)) {
                throw new NoSuchObjectException("accountChangeEvent is null.");
            }
            //update the account balance using the AccountChangeEvent object
            accountInfoService.doUpdateAccountBalance(accountChangeEvent);
            //return a commit state
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            //log the error model
            log.error("execute Local Transaction is fail, error: {}", e.getCause().getMessage());
            //return a rollback state
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 本地事务回查接口
     *
     * @param msg
     * @return {@link RocketMQLocalTransactionState}
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        try {
            //获取消息的payload
            String msgStr = new String((byte[]) msg.getPayload());
            //从payload中获取指定对象
            AccountChangeEvent accountChange = JsonUtil.getSpecifiedObj(msgStr, "accountChange", AccountChangeEvent.class);
            if (accountChange == null) {
                log.debug("msg don't contain specified object.");
                return null;
            }
            //查询数据库中是否存在该数据
            int existsValue = duplicateMapper.countByTxno(accountChange.getTxNo());
            if (existsValue > 0) {
                //数据存在返回commit
                return RocketMQLocalTransactionState.COMMIT;
            }
            //数据不存在返回rollback
            return RocketMQLocalTransactionState.UNKNOWN;
        } catch (Exception e) {
            log.error("check local transaction is fail, error: {}", e.getCause().getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
