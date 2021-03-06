package com.it.stock.mapper;

import com.it.stock.common.domain.StockBlockDomain;
import com.it.stock.pojo.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author jian12
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockDomain> getStockBlock(@Param("date") Date date, @Param("num") Integer num);

    Integer addStockBusiness(@Param("list") List list);

    List<StockBlockDomain> findBlockInfoByTimeLimit(@Param("date")Date curDate);
}
