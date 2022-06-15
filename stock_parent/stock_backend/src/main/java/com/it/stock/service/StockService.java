package com.it.stock.service;

import com.it.stock.common.domain.*;
import com.it.stock.vo.resp.PageResult;
import com.it.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 国内指数
 */
public interface StockService {
    /**
     * 查询国内大盘指数
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarket();
    /**
     * 查询国内板块指数
     * @return
     */
    R<List<StockBlockDomain>> getStockBlock();

    /**
     * 涨幅榜单
     * @return
     */
    R<List<StockUpdownDomain>> getStockUpdownLimit();
    /**
     * 涨幅列表分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    R<PageResult<StockUpdownDomain>> getStockUpdowPage(Integer page, Integer pageSize);
    /**
     * 涨跌停
     * @return
     */
    R<Map<String, List>> getStockUpDownCount();
    /**
     * 导出数据
     * @param response
     * @param page
     */
    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);



    /**
     * 深证 上证 成交量
     * @return
     */
    R<Map<String,List>> getVomule();
    /**
     * 个股分时涨跌幅度统计功能
     * @return
     */
    R<Map> getStockUpDown();
    /**
     * 个股时分K线图
     * @param code
     * @return
     */
    R<List<Stock4MinuteDomain>> getStockInfoByCode(String code);
    /**
     * 个股日k图
     * @param code
     * @return
     */
    R<List<Stock4EvrDayDomain>> getStockInfoEvrDay(String code);


    /**
     *外盘指数功能
     * @return
     */
    R<List<OuterMarketDomain>> outerIndexAll();

    /**
     * 根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     * @param searchStr
     * @return
     */
    R<List> searchStock(String searchStr);

    /**
     * 个股主营业务查询
     */
    R<Map> getSingleStockInfo(String code);

    /**
     * 个股周K线功能实现
     * @param code
     * @return
     */
    R<List<StockWeekDomain>> getStockWeekInfo(String code);

    /**
     * 个股分时行情数据
     * @param code
     * @return
     */
    R<SingleStockInfo> getSingleStockInfoByTime(String code);

    /**
     * 个股实时交易流水查询
     */

    R<List<SingleStockTradeDetailInfo>> getSingleStockTradeDetailInfo(String code);
}
