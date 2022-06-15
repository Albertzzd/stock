package com.itheima.stock.common.domain;

import lombok.Data;

/**
 * 个股描述
 */
@Data
public class RtInfoDesDomain {
    private String code;//编码
    private String trade;//行业
    private String business;//描述
    private String name;//公司名称
}
