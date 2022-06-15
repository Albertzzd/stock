package com.it.stock.mapper;

import com.it.stock.pojo.SysUser;
import com.it.stock.vo.resp.UserPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jian12
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.SysUser
*/
@Mapper
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser queryUserByUserName(@Param("username") String username);
    /**
     * 多条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @return
     */
    List<UserPage> selectByPageAllUser(@Param("userName") String userName, @Param("nickName") String nickName,
                                       @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser findUserByUserName(@Param("username") String username);
}
