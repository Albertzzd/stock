package com.it.stock.mapper;

import com.it.stock.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jian12
* @description 针对表【sys_user_role(用户角色表)】的数据库操作Mapper
* @createDate 2022-05-30 17:29:37
* @Entity com.itheima.stock.pojo.SysUserRole
*/
public interface SysUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<String> selectUserRole(@Param("userId") String userId);

    void deleteByUserId(@Param("userId")String userId);

    int insertBatch(@Param("list")List<SysUserRole> list);
}
