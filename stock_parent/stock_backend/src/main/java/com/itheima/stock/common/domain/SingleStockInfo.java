package com.itheima.stock.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SingleStockInfo {
   private BigDecimal preClosePrice;
   private BigDecimal openPrice;
   private BigDecimal tradePrice;
   private BigDecimal highPrice;
   private BigDecimal lowPrice;
   private BigDecimal tradeVol;
   private Long tradeAmt;
   @JsonFormat(pattern = "yyyyMMddHHmm")
   private Date curDate;
}
