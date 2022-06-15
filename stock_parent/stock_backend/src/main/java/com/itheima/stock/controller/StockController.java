package com.itheima.stock.controller;

import com.itheima.stock.common.domain.*;
import com.itheima.stock.pojo.StockOuterMarketIndexInfo;
import com.itheima.stock.service.StockService;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 国内指数
 */
@RestController
@RequestMapping("/api/quot")
public class StockController {
    @Autowired
    StockService stockService;



    /**
     * 个股日k图
     * @param code
     * @return
     */
    @GetMapping("/stock/screen/dkline")
    public R<List<Stock4EvrDayDomain>> getStockInfoEvrDay(@RequestParam("code") String code){
       return stockService.getStockInfoEvrDay(code);
    }

    /**
     * 个股时分K线图
     * @param code
     * @return
     */
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockInfoByCode(@RequestParam("code") String code){
        return stockService.getStockInfoByCode(code);
    }

    /**
     * 个股分时涨跌幅度统计功能
     * @return
     */
    @GetMapping("/stock/updown")
    public R<Map> getStockUpDown(){
return stockService.getStockUpDown();
    }

    /**
     * 深证 上证 成交量
     * @return
     */
    @GetMapping("/stock/tradevol")
    public R<Map<String,List>> getVomule() {
        return stockService.getVomule();
    }


    /**
     * 国内大盘指数
     *
     * @return
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarket() {
        return stockService.getInnerMarket();
    }

    /**
     * 国内板块
     *
     * @return
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> getStockBlock() {
        return stockService.getStockBlock();
    }

    /**
     * 涨幅榜单
     *
     * @return
     */
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> getStockUpdownLimit() {
        return stockService.getStockUpdownLimit();
    }

    /**
     * 涨幅列表分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> getStockUodownByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return stockService.getStockUpdowPage(page, pageSize);
    }

    /**
     * 涨跌停
     *
     * @return
     */
    @GetMapping("/stock/updown/count")
    public R<Map<String, List>> getStockUpDownCount() {
        return stockService.getStockUpDownCount();
    }

    /**
     * 导出数据
     *
     * @param response
     * @param page
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response,
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {

        stockService.stockExport(response, page, pageSize);
    }
    /**
     * 外盘指数展示
     */
    @GetMapping("/external/index")
    public R<List<OuterMarketDomain>> outerIndexAll(){
        return stockService.outerIndexAll();
    }

    /**
     * 根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     */
    @GetMapping("/stock/search")
    public R<List> searchStock(@RequestParam("searchStr") String searchStr){
        return stockService.searchStock(searchStr);
    }

    /**
     * 个股主营业务查询
     */
    @GetMapping("/stock/describe")
    public R<Map> getSingleStockInfo(@RequestParam("code") String code){
        return stockService.getSingleStockInfo(code);
    }
    /**
     * 个股周K线功能实现
     */
    @GetMapping("/stock/screen/weekkline")
    public R<List<StockWeekDomain>> getStockWeekInfo(@RequestParam("code") String code){
        return stockService.getStockWeekInfo(code);
    }
    /**
     * 个股分时行情数据
     */
    @GetMapping("/stock/screen/second/detail")
    public R<SingleStockInfo> getSingleStockInfoByTime(@RequestParam("code") String code){
        return stockService.getSingleStockInfoByTime(code);
    }
    /**
     * 个股实时交易流水查询
     */
    @GetMapping("/stock/screen/second")
    public R<List<SingleStockTradeDetailInfo>> getSingleStockTradeDetailInfo(@RequestParam("code") String code){
        return stockService.getSingleStockTradeDetailInfo(code);
    }
}
