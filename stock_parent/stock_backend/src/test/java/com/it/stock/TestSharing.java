package com.it.stock;

import com.it.stock.common.domain.StockBlockDomain;
import com.it.stock.common.domain.StockWeekDomain;
import com.it.stock.mapper.StockBlockRtInfoMapper;
import com.it.stock.mapper.StockBusinessMapper;
import com.it.stock.mapper.StockRtInfoMapper;
import com.it.stock.mapper.SysUserMapper;
import com.it.stock.pojo.StockBusiness;
import com.it.stock.pojo.SysUser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestSharing {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 测试默认数据源
     */
    @Test
    public void testDefaultDs(){
        SysUser user = sysUserMapper.selectByPrimaryKey(1237361915165020161l);
        System.out.println(user);
    }

    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    /**
     * @Description 测试广播表
     */
    @Test
    public void testBroadCastTable(){
        List<StockBusiness> all = stockBusinessMapper.getAll();
        System.out.println(all);
    }

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Test
    public void testCommonSharding(){
        Date curDate= DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockBlockDomain> info = stockBlockRtInfoMapper.findBlockInfoByTimeLimit(curDate);
        System.out.println(info);
    }

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    /**
     * @Description 测试分库分表算法类
     */
    @Test
    public void testShardingDbAndTb(){
        //截止时间
        Date endTime=DateTime.parse("2022-05-22 09:30:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //开始时间
        Date startTime=DateTime.parse("2021-01-01 09:30:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //根据指定日期范围查询周K线数据
        List<StockWeekDomain> infos=stockRtInfoMapper.getHalfWeekLineData("000017",startTime,endTime);
        System.out.println(infos);
    }
}