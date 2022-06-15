package com.itheima.stock;

import com.itheima.stock.config.StockInfoConfig;
import com.itheima.stock.config.TaskThreadPoolInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.itheima.stock.mapper")
@EnableConfigurationProperties({StockInfoConfig.class, TaskThreadPoolInfo.class})
public class StockSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockSpringBootApplication.class, args);
    }

}
