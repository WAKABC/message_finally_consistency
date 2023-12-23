package com.wak.bank1.service;

import com.wak.bank1.entities.AccountChangeEvent;

/**
 * @author wuankang
 * @version 1.0.0
 * @data 2023/11/05
 * @description TODO
 * @see
 */
public interface AccountInfoService {

    /**
     * 向mq发送转账消息
     * @param accountChangeEvent
     */
    void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent);

    /**
     * 执行本地事务
     * @param accountChangeEvent
     */
    void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent);
}
