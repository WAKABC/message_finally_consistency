package com.wak.bank1.controller;

import com.wak.bank1.entities.AccountChangeEvent;
import com.wak.bank1.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wuankang
 * @date 2023/11/2 15:59
 * @Description TODO
 * @Version 1.0
 */
@RestController
@Slf4j
public class AccountInfoController {
    @Resource
    private AccountInfoService service;

    @GetMapping("/transfer")
    public String transfer(@RequestParam("acctNo") String accountNo, @RequestParam("amount") double amount) {
        log.info("开始转账");
        String txNo = UUID.randomUUID().toString();
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent(accountNo, amount, txNo);
        //通过rocketmq发送半事务消息
        service.sendUpdateAccountBalance(accountChangeEvent);
        return "转账成功";
    }
}
