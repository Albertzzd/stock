package com.itheima.stock.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SingleStockTradeDetailInfo {
    @JsonFormat(pattern = "yyyyMMddHHmm")
    private Date date ;
    private Long tradeAmt;
    private BigDecimal tradeVol;
    private BigDecimal tradePrice;
}
