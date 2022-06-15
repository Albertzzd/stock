package com.itheima.stock.mapper;

import com.itheima.stock.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jian12
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll();

    int deleteUserById(@Param("userIds")List<Long> userIds);

    /**
     * 根据用户id查询角色信息
     * @param userId
     * @return
     */
    List<SysRole> getRoleByUserId(@Param("userId") String userId);
}
