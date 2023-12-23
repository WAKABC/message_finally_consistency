package com.wak.bank1.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/6 16:27
 * @Description TODO
 * @Version 1.0
 */

/**
 * account_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_info")
public class AccountInfo {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 帐户名称
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 帐户号码
     */
    @Column(name = "account_no")
    private String accountNo;

    /**
     * 帐户余额
     */
    @Column(name = "account_balance")
    private Double accountBalance;
}