package com.wak.bank2.service;

import com.wak.bank2.entities.AccountChangeEvent;

/**
 * @author wuankang
 * @version 1.0.0
 * @data 2023/11/05
 * @description TODO
 * @see
 */
public interface AccountInfoService {

    /**
     * 跟新帐户余额
     * @param accountChangeEvent
     */
    void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent);
}
