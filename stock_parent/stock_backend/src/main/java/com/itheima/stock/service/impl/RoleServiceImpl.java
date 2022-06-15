package com.itheima.stock.service.impl;

import com.itheima.stock.mapper.SysRoleMapper;
import com.itheima.stock.mapper.SysUserRoleMapper;
import com.itheima.stock.pojo.SysRole;
import com.itheima.stock.pojo.SysUserRole;
import com.itheima.stock.service.RoleService;
import com.itheima.stock.utils.IdWorker;
import com.itheima.stock.vo.req.UpdateMsgReqVo;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.ResponseCode;
import com.itheima.stock.vo.resp.UserRoleRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private IdWorker idWorker;
    /**
     * 获取用户具有的角色信息，以及所有的角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public R<UserRoleRespVo> getRoleMsg(String userId) {
        List<String> role = sysUserRoleMapper.selectUserRole(userId);
        List<SysRole> roles = sysRoleMapper.selectAll();
        UserRoleRespVo userRoleRespVo = new UserRoleRespVo();
        userRoleRespVo.setAllRole(roles);
        userRoleRespVo.setOwnRoleIds(role);

        return R.ok(userRoleRespVo);
    }

    //更新用户角色信息
    @Override
    public R updateMsg(UpdateMsgReqVo updateMsgReqVo) {
        //1.判断用户id是否存在
        if (updateMsgReqVo.getUserId() == null) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        //2.删除用户原来所拥有的角色id
        this.sysUserRoleMapper.deleteByUserId(updateMsgReqVo.getUserId());
        //如果对应集合为空，则说明用户将所有角色都清除了
        if (CollectionUtils.isEmpty(updateMsgReqVo.getRoleIds())) {
            return R.ok(ResponseCode.SUCCESS.getMessage());
        }
        List<SysUserRole> list = updateMsgReqVo.getRoleIds().stream().map(roleId -> {
            SysUserRole userRole = SysUserRole.builder().
                    userId(updateMsgReqVo.getUserId()).roleId(roleId).
                    createTime(new Date()).id(idWorker.nextId() + "").build();
            return userRole;
        }).collect(Collectors.toList());
        //批量插入
        int count = this.sysUserRoleMapper.insertBatch(list);
        if (count == 0) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public R deleteByIds(List<Long> userIds) {
        int count = sysRoleMapper.deleteUserById(userIds);
        if (count == 0) {
            return R.error("删除失败");
        }
        log.info("删除数量{}", count);
        return R.ok();
    }
}
