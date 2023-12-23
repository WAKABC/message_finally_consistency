package com.wak.bank2.mapper;
import org.apache.ibatis.annotations.Param;

import com.wak.bank2.entities.Duplicate;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author wuankang
 * @version 1.0.0
 * @date 2023/11/06
 * @description TODO
 */
public interface DuplicateMapper extends Mapper<Duplicate> {
    /**
     * 插入
     *
     * @param txNo 流水号
     * @return int
     */
    int addDuplicate(@Param("txno") String txNo);

    /**
     * 幂等判断
     * @param txno txno
     * @return {@code Integer}
     */
    Integer countByTxno(@Param("txno")String txno);
}