package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.pojo.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author jian12
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    List<InnerMarketDomain> getInnerMarketByDateAndInner(@Param("date") Date date,@Param("inner") List<String> inner);

    List<Map> getVomule(@Param("endDate") Date endDate, @Param("starDate") Date starDate, @Param("inner") List<String> inner);

    Integer addInnerMarket(@Param("innerList") List innerList);
}
