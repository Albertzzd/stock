package com.it.stock.mapper;

import com.it.stock.common.domain.RtInfoDesDomain;
import com.it.stock.pojo.StockBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author jian12
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<String> getAllCode();

    RtInfoDesDomain getRtInfoDes(@Param("code") String code);

    //查询个股参数
    Map<String, String> getSingleStockInfo(@Param("code") String code);

    //查询所有
    List<StockBusiness> getAll();
}
