package com.itheima.stock.task.service;

public interface StockTimerTaskService {
    /**
     * 拉取 A股各股数据
     */
    void getStockRtIndex();
    /**
     * 拉取国内上 深 大盘数据
     */
    void getInnerMarket();

    /**
     * 拉取板块数据
     */
    void getStockBusiness();


    /**
     * 拉取外盘数据功能实现
     */
    void getOuterMarket();

}
