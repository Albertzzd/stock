package com.it.stock.vo.resp;

import com.it.stock.pojo.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleRespVo {
    /**
     * 用户用户的角色id集合
     */
    private List<String> ownRoleIds;
    /**
     * 所有角色集合
     */
    private List<SysRole> allRole;
}