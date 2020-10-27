package cn.jackbin.SimpleRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 花费月份详情
 * @date: 2020/10/27 22:01
 **/
public class SpendMonthDetailDTO {

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date month;

    /**
     * 支出总额
     */
    private Double expendTotal;

    /**
     * 收入总额
     */
    private Double incomeTotal;
}
