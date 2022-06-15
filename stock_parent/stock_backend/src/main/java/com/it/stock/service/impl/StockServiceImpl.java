package com.it.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.it.stock.common.domain.*;
import com.it.stock.mapper.*;
import com.it.stock.utils.DateTimeUtil;
import com.it.stock.vo.resp.PageResult;
import com.it.stock.vo.resp.R;
import com.it.stock.vo.resp.ResponseCode;
import com.it.stock.config.StockInfoConfig;
import com.it.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 国内大盘指数
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    StockInfoConfig stockInfoConfig;

    @Autowired
    StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;

    @Autowired
    StockBusinessMapper stockBusinessMapper;


    /**
     * 查询国内大盘指数
     *
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> getInnerMarket() {
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //伪数据
        date = DateTime.parse("2021-12-28 09:31:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<String> inner = stockInfoConfig.getInner();
        List<InnerMarketDomain> list = stockMarketIndexInfoMapper.getInnerMarketByDateAndInner(date, inner);
        return R.ok(list);
    }
    /**
     * 外盘指数功能展示
     * @return
     */

    @Override
    public R<List<OuterMarketDomain>> outerIndexAll() {
        //获取国外大盘的id集合
        List<String> outerIds = stockInfoConfig.getOuter();
        //获取当前时间:最近的股票时间
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //mock date
        Date lastDate = DateTime.parse("2022-05-18 15:58:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<OuterMarketDomain> list=stockOuterMarketIndexInfoMapper.getMarketInfo(outerIds,lastDate);

        if(CollectionUtils.isEmpty(list)){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }

        return R.ok(list);
    }

    /**
     * 查询国内板块
     *
     * @return
     */
    @Override
    public R<List<StockBlockDomain>> getStockBlock() {
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //伪数据
        date = DateTime.parse("2021-12-27 10:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        Integer num = 10;
        List<StockBlockDomain> list = stockBlockRtInfoMapper.getStockBlock(date, num);
        return R.ok(list);
    }

    /**
     * 涨幅榜单
     *
     * @return
     */
    @Override
    public R<List<StockUpdownDomain>> getStockUpdownLimit() {
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //伪数据
        date = DateTime.parse("2021-12-27 10:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        PageHelper.startPage(1, 10);
        List<StockUpdownDomain> list = stockRtInfoMapper.getStockUpdown(date);
        return R.ok(list);
    }

    /**
     * 涨幅列表分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockUpdowPage(Integer page, Integer pageSize) {
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //伪数据
        date = DateTime.parse("2021-12-27 10:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        PageHelper.startPage(page, pageSize);
        List<StockUpdownDomain> list = stockRtInfoMapper.getStockUpdown(date);
        if (CollectionUtils.isEmpty(list)) {
            return R.error("暂无数据");
        }
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(list);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);
        return R.ok(pageResult);
    }

    /**
     * 涨跌停
     *
     * @return
     */
    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        //获取当前时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endTime = endDateTime.toDate();
        //获取最近开盘时间
        DateTime starDateTime = DateTimeUtil.getOpenDate(endDateTime);
        Date starTime = starDateTime.toDate();
        // 伪数据
        endDateTime = DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        endTime = endDateTime.toDate();
        starDateTime = DateTimeUtil.getOpenDate(endDateTime);
        starTime = starDateTime.toDate();
        //1表示涨停 0表示跌停
        List<Map> upList = stockRtInfoMapper.getStockUpDownCount(starTime, endTime, 1);
        List<Map> downList = stockRtInfoMapper.getStockUpDownCount(starTime, endTime, 0);
        HashMap<String, List> map = new HashMap<>();
        map.put("upList", upList);
        map.put("downList", downList);
        return R.ok(map);
    }

    /**
     * 导出数据
     *
     * @param response
     * @param page
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.设置响应数据的类型:excel
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");

            Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
            //伪数据
            date = DateTime.parse("2021-12-27 10:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            PageHelper.startPage(page, pageSize);
            List<StockUpdownDomain> list = stockRtInfoMapper.getStockUpdown(date);

            //EasyExcel导出
            EasyExcel.write(response.getOutputStream(), StockUpdownDomain.class).sheet("股票数据").doWrite(list);
        } catch (Exception e) {
            log.info("股票excel数据导出异常，当前页：{}，每页大小：{}，异常信息：{}", page, pageSize, e.getMessage());
        }

    }


    /**
     * 深证 上证 成交量
     *
     * @return
     */
    @Override
    public R<Map<String, List>> getVomule() {
        //T日时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endDate = endDateTime.toDate();
        Date starDate = DateTimeUtil.getOpenDate(endDateTime).toDate();
        //伪数据
        starDate = DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endDate = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //T-1日时间
        DateTime yesEndDateTime = DateTimeUtil.getPreviousTradingDay(endDateTime);
        Date yesEndDate = yesEndDateTime.toDate();
        Date yesStarDate = DateTimeUtil.getOpenDate(yesEndDateTime).toDate();
        //伪数据
        yesStarDate = DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        yesEndDate = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //获取深上a股大盘id
        List<String> inner = stockInfoConfig.getInner();
        //最新成交量
        List<Map> volList = stockMarketIndexInfoMapper.getVomule(endDate, starDate, inner);
        //前一交易日交易量
        List<Map> yesVolList = stockMarketIndexInfoMapper.getVomule(yesEndDate, yesStarDate, inner);

        HashMap<String, List> infoMap = new HashMap<>();
        infoMap.put("volList", volList);
        infoMap.put("yesVolList", yesVolList);
        return R.ok(infoMap);
    }

    /**
     * 个股分时涨跌幅度统计功能
     *
     * @return
     */
    @Override
    public R<Map> getStockUpDown() {
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date date = dateTime.toDate();
        //伪数据
        date = DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<Map> list = stockRtInfoMapper.getStockUpDown(date);
        //获取去股票涨幅区间的集合
        List<String> upDownRange = stockInfoConfig.getUpDownRange();
        //将list集合下的字符串映射成Map对象
        List<Map> orderMap = upDownRange.stream().map(title -> {
            Optional<Map> op = list.stream().filter(m -> m.containsValue(title)).findFirst();
            //判断对应的map是否存在
            Map tmp = null;
            if (op.isPresent()) {
                tmp = op.get();
            } else {
                tmp = new HashMap();
                tmp.put("title", title);
                tmp.put("count", 0);
            }
            return tmp;
        }).collect(Collectors.toList());
        HashMap<String, Object> mapInfo = new HashMap<>();
        mapInfo.put("time", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        mapInfo.put("infos", orderMap);
        return R.ok(mapInfo);
    }

    /**
     * 个股时分K线图
     *
     * @param code
     * @return
     */
    @Override
    public R<List<Stock4MinuteDomain>> getStockInfoByCode(@RequestParam("code") String code) {
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date starDate = DateTimeUtil.getOpenDate(dateTime).toDate();
        Date endDate = dateTime.toDate();

        //伪数据
        endDate = DateTime.parse("2021-12-30 14:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        starDate = DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<Stock4MinuteDomain> list = stockRtInfoMapper.getStockInfoByCode(starDate, endDate, code);
        return R.ok(list);
    }

    /**
     * 个股日k图
     *
     * @param code
     * @return
     */
    @Override
    public R<List<Stock4EvrDayDomain>> getStockInfoEvrDay(String code) {
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date starDate = dateTime.minusDays(500).toDate();
        Date endDate = dateTime.toDate();
        List<Stock4EvrDayDomain> list = stockRtInfoMapper.getStockInfoEvrDay(starDate, endDate, code);
        return R.ok(list);
    }

    /**
     * 模糊查找股票信息
     * @param searchStr
     * @return
     */
    @Override
    public R<List> searchStock(String searchStr) {
        //传入参数判断
        if(Strings.isNullOrEmpty(searchStr)){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //调用业务层方法查询
        List<Map> list =stockRtInfoMapper.searchStock(searchStr);
        //返回结果判断
        if(CollectionUtils.isEmpty(list) || list ==null){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(list);
    }

    /**
     * 个股信息查询
     * @param code
     * @return
     */

    @Override
    public R<Map> getSingleStockInfo(String code) {
        //参数判断
        if(Strings.isNullOrEmpty(code)){
            R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //业务查询
        Map<String,String> maps = stockBusinessMapper.getSingleStockInfo(code);
        //查询结果判断
        if(CollectionUtils.isEmpty(maps) || maps ==null){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(maps);
    }

    /**
     * 个股周K线功能实现
     * @param code
     * @return
     */
    @Override
    public R<List<StockWeekDomain>> getStockWeekInfo(String code) {
        //参数判断
        if(Strings.isNullOrEmpty(code)){
            R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //获取开始日期和结束日期
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date starDate = DateTimeUtil.getOpenDate(dateTime).toDate();
        Date endDate = dateTime.toDate();
        //mock data
        endDate = DateTime.parse("2022-06-02 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        starDate = DateTime.parse("2021-12-19 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockWeekDomain> list = stockRtInfoMapper.getStockWeekInfo(code,starDate,endDate);
        //查询结果判断
        if(CollectionUtils.isEmpty(list) || list ==null){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(list);
    }

    /**
     * 个股分时行情数据
     * @param code
     * @return
     */
    @Override
    public R<SingleStockInfo> getSingleStockInfoByTime(String code) {
        //参数判断
        if(Strings.isNullOrEmpty(code)){
            R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //获取当前股票有效时间
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date date = dateTime.toDate();
        //mock data
        Date lastDate = DateTime.parse("2021-12-30 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        SingleStockInfo info =stockRtInfoMapper.getSingleStockInfoByTime(code,lastDate);
        //返回值结果判断
        if(Strings.isNullOrEmpty(info.toString())){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(info);
    }

    /**
     * 个股实时交易流水查询
     * @param code
     * @return
     */
    @Override
    public R<List<SingleStockTradeDetailInfo>> getSingleStockTradeDetailInfo(String code) {
        //参数判断
        if(Strings.isNullOrEmpty(code)){
            R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        //定义查询数量
        int limitNum = 10;
        List<SingleStockTradeDetailInfo> list =stockRtInfoMapper.getSingleStockTradeDetailInfo(code,limitNum);
        //查询结果判断
        if(CollectionUtils.isEmpty(list) || list ==null){
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return R.ok(list);
    }

}
