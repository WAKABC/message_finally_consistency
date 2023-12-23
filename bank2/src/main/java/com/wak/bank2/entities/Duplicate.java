package com.wak.bank2.entities;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuankang
 * @date 2023/11/6 15:07
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Duplicate {
    /**
     * 流水号
     */
    private String txno;

    /**
     * 创建时间
     */
    private Date createtime;
}