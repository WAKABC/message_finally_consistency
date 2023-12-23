package com.wak.bank1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帐户转账消息模型
 * @author wuankang
 * @date 2023/11/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChangeEvent{
    /**
     * 帐户号码
     */
    private String accountNo;
    /**
     * 变动金额
     */
    private double amount;
    /**
     *  交易流水号
     */
    private String txNo;
}
