package com.wak.bank2.controller;

import com.wak.bank2.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        return "";
    }
}
