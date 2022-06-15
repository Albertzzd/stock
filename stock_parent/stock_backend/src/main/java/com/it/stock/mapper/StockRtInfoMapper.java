package com.it.stock.mapper;

import com.it.stock.common.domain.*;
import com.it.stock.pojo.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author jian12
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> getStockUpdown(Date date);

    List<Map> getStockUpDownCount(@Param("starTime") Date starTime, @Param("endTime") Date endTime,@Param("flag") Integer i);

    List<Map> getSearch(@Param("searchStr") String searchStr, @Param("date") Date date);

    List<Map> getStockUpDown(@Param("date") Date date);

    List<Stock4MinuteDomain> getStockInfoByCode(@Param("starDate") Date starDate, @Param("endDate") Date endDate, @Param("code") String code);

    List<Stock4EvrDayDomain> getStockInfoEvrDay(@Param("starDate") Date starDate, @Param("endDate") Date endDate, @Param("code") String code);

    Integer addStockRtIndex(@Param("list") List list);

    List<StockWeekDomain> getStockInfoWeek(@Param("endDate") Date endDate, @Param("starDate") Date starDate, @Param("code") String code);

    /**
     * 模糊查询
     * @param searchStr
     * @return
     */
    List<Map> searchStock(@Param("searchStr") String searchStr);
    /**
     * 个股周K线功能实现
     * @param code
     * @return
     */
    List<StockWeekDomain> getStockWeekInfo(@Param("code")String code,
                                           @Param("starDate")Date starDate,
                                           @Param("endDate")Date endDate);

    /**
     * 个股分时行情数据
     * @param code
     * @return
     */
    SingleStockInfo getSingleStockInfoByTime(@Param("code")String code,
                                             @Param("lastDate")Date lastDate);

    /**
     * 个股实时交易流水查询
     * @param code
     * @param limitNum
     * @return
     */
    List<SingleStockTradeDetailInfo> getSingleStockTradeDetailInfo(@Param("code")String code,
                                                                   @Param("limitNum")Integer limitNum);

    List<StockWeekDomain> getHalfWeekLineData(@Param("code")String code,
                                              @Param("startTime")Date startTime,
                                              @Param("endTime")Date endTime);
}
