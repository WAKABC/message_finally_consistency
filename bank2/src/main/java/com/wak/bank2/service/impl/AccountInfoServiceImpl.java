package com.wak.bank2.service.impl;

import com.wak.bank2.entities.AccountChangeEvent;
import com.wak.bank2.mapper.AccountInfoMapper;
import com.wak.bank2.mapper.DuplicateMapper;
import com.wak.bank2.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
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


    /**
     * 跟新账户余额
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
    }
}
