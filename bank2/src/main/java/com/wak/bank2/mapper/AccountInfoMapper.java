package com.wak.bank2.mapper;

import com.wak.bank2.entities.AccountInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wuankang
 * @date 2023/11/6 16:27
 * @Description TODO
 * @Version 1.0
 */
public interface AccountInfoMapper extends Mapper<AccountInfo> {
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("accountBalance") double accountBalance);
}