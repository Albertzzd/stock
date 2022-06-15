package com.itheima.stock.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 外盘指数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OuterMarketDomain {
    private String name;//大盘名称

    private BigDecimal curPoint;//当前大盘点

    private BigDecimal upDown;//涨跌值

    private BigDecimal rose;//涨幅

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curTime;//当前时间
}
