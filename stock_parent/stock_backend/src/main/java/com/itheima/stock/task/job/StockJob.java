package com.itheima.stock.task.job;

import com.itheima.stock.task.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StockJob {
    @Autowired
   StockTimerTaskService stockTimerTaskService;

    /**
     * 国内大盘数据定时任务
     */
    @XxlJob("getInnerMarket")
    public void getInnerMarket(){
        stockTimerTaskService.getInnerMarket();
    }

    /**
     * 个股数据定时任务
     */
    @XxlJob("getStockRtIndex")
    public void getStockRtIndex(){
        stockTimerTaskService.getStockRtIndex();
    }

    /**
     * 板块数据定时任务
     */
    @XxlJob("getStockBusiness")
    public void getStockBusiness(){
        stockTimerTaskService.getStockBusiness();
    }


}
