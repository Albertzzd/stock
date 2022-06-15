package com.it.stock.mapper;

import com.it.stock.common.domain.OuterMarketDomain;
import com.it.stock.pojo.StockOuterMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author jian12
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    List<OuterMarketDomain> getOuterMarket(@Param("outer") List<String> outer , @Param("num") Integer num);

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);

    Integer addOuterMarket(@Param("list") List list);
    //查询外盘指数
    List<OuterMarketDomain> getMarketInfo(@Param("outerIds") List<String> outerIds,
                                          @Param("lastDate") Date lastDate);
}
