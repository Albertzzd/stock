package com.it.stock;


import com.it.stock.task.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskTast {
    @Autowired
    StockTimerTaskService stockTimerTaskService;

    @Test
    public void a() {
        stockTimerTaskService.getInnerMarket();
    }

    @Test
    public void b() {
        stockTimerTaskService.getStockRtIndex();
    }

    @Test
    public void c() {
//       stockTimerTaskService.getStockBusiness();
//       stockTimerTaskService.getOuterMarket();
    }

    @Test
    public void getOuterMarketInfo(){
        stockTimerTaskService.getOuterMarket();
    }

}
