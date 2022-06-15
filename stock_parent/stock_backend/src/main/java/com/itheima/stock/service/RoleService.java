package com.itheima.stock.service;

import com.itheima.stock.vo.req.UpdateMsgReqVo;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.UserRoleRespVo;

import java.util.List;

public interface RoleService {

    //获取用户的角色信息
    R<UserRoleRespVo> getRoleMsg(String userId);

    //更新用户角色信息
    R updateMsg(UpdateMsgReqVo updateMsgReqVo);

    //批量删除用户信息
    R deleteByIds(List<Long> userIds);
}
