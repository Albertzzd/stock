package com.itheima.stock.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 个股详情信息表
 * @TableName stock_rt_info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRtInfo implements Serializable {
    /**
     * 主键字段（无业务意义）
     */
    private String id;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 当前时间
     */

    private Date curTime;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 前收盘价| 昨日收盘价
     */
    private BigDecimal preClosePrice;

    /**
     * 当前价格
     */
    private BigDecimal curPrice;

    /**
     * 今日最高价
     */
    private BigDecimal maxPrice;

    /**
     * 今日最低价
     */
    private BigDecimal minPrice;

    /**
     * 成交总量
     */
    private Long tradeAmount;
    /**
     * 成交金额(单位元)
     */
    private BigDecimal tradeVolume;

    private static final long serialVersionUID = 1L;
}