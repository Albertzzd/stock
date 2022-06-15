package com.it.stock.task.service.impl;

import com.google.common.collect.Lists;
import com.it.stock.mapper.*;
import com.it.stock.utils.ParseType;
import com.it.stock.config.StockInfoConfig;
import com.it.stock.task.service.StockTimerTaskService;
import com.it.stock.utils.IdWorker;
import com.it.stock.utils.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service("stockTimerTaskService")
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StockInfoConfig stockInfoConfig;

    @Autowired
    IdWorker idWorker;

    @Autowired
    ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    StockBusinessMapper stockBusinessMapper;

    @Autowired
    StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;

    /**
     * 拉取国内上 深 大盘数据
     */
    @Override
    public void getInnerMarket() {
        //获取url
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        //获取请求头对象并组装请求对象
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //发送请求并获取数据
        String innerMarket = restTemplate.postForObject(url, entity, String.class);
        //解析数据
        List innerList = parserStockInfoUtil.parser4StockOrMarketInfo(innerMarket, ParseType.INNER);
        //采集日志
        log.info("采集国内大盘数据：{}条", innerList.size());
        //批量存数据
        Integer num = stockMarketIndexInfoMapper.addInnerMarket(innerList);
        //采集日志
        log.info("存入国内大盘数据：{}条", num);
    }

    /**
     * 拉取 A股各股数据
     */
    @Override
    public void getStockRtIndex() {
        //获取A股code码
        List<String> codes = stockBusinessMapper.getAllCode();
        //组装 code码 上 sh 深 sz
        codes = codes.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        // 组装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //分片 请求数据
        Lists.partition(codes, 20).forEach(codeList -> {
            // 获取请求url
            String url = stockInfoConfig.getMarketUrl() + String.join(",", codeList);
            String info = restTemplate.postForObject(url, entity, String.class);
            //解析数据
            List list = parserStockInfoUtil.parser4StockOrMarketInfo(info, ParseType.ASHARE);
            log.info("采集A股数据：{}条", list.size());
            //存储数据
            Integer num = stockRtInfoMapper.addStockRtIndex(list);
            log.info("存入A股数据：{}条", num);
        });
    }

    /**
     * 拉取板块数据
     */
    @Override
    public void getStockBusiness() {
        //获取url
        String url = stockInfoConfig.getBlockUrl();
        //获取请求数据
        String info = restTemplate.getForObject(url, String.class);
        //解析数据
        List list = parserStockInfoUtil.parse4StockBlock(info);
        log.info("采集板块数据：{}条", list.size());
        //存储数据
        Integer num = stockBlockRtInfoMapper.addStockBusiness(list);
        log.info("存入板块数据：{}条", num);
    }

    /**
     * 拉取外盘股票数据
     */
    @Override
    public void getOuterMarket() {
        //获取url
        String url = stockInfoConfig.getMarketUrl()+String.join(",",stockInfoConfig.getOuter()) ;
        //获取请求头对象并组装数据
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer","http://finance.sina.com.cn/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        //发起请求并获取数据
        String outerMarket = restTemplate.postForObject(url, entity, String.class);
        //解析数据
        List outerList = parserStockInfoUtil.parser4StockOrMarketInfo(outerMarket, 2);
        //采集日志
        log.info("采集国外大盘数量"+outerList.size());
        //存入数据
        Integer num = stockOuterMarketIndexInfoMapper.addOuterMarket(outerList);
        log.info("插入外盘数据:{}条",num);
    }


}
