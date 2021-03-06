package com.it.stock.mapper;

import com.it.stock.pojo.SysLog;

/**
* @author jian12
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
