package com.it.stock.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 周k图
 */
@Data
public class StockWeekDomain {
    private BigDecimal avgPrice;//一周内平均价
    private BigDecimal minPrice;//一周内最低价
    private BigDecimal openPrice;//周一开盘价
    private BigDecimal maxPrice;//一周内最高价
    private BigDecimal closePrice;//周五收盘价（如果当前日期不到周五，则显示最新价格）
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date mxTime;//一周内最大时间
    private String stockCode;//股票编码
}
