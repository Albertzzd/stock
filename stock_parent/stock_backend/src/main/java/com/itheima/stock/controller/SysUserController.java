package com.itheima.stock.controller;

import com.itheima.stock.service.RoleService;
import com.itheima.stock.service.SysUserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.req.UpdateMsgReqVo;
import com.itheima.stock.vo.req.UserAddReqVo;
import com.itheima.stock.vo.req.UserReqVo;
import com.itheima.stock.vo.resp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 登录
 */
@RestController
@RequestMapping("/api")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private RoleService roleService;

    /**
     * 登录
     * @param vo
     * @return
     */
//    @PostMapping("/login")
//    public R<LoginRespVo> longin(@RequestBody LoginReqVo vo){
//        return sysUserService.login(vo);
//    }

    /**
     * 验证码
     * @return
     */
    @GetMapping("/captcha")
    public R<Map> getCaptcha(){return sysUserService.getCaptcha();}

    /**
     * 条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userReqVo
     * @return
     */
    @PostMapping("/users")
    public R<PageResult<UserPage>> userSelect(@RequestBody UserReqVo userReqVo) {
        return sysUserService.selectByUser(userReqVo);
    }
    /**
     *添加用户信息
     * @param userAddReqVo
     * @return
     */
    @PostMapping("/user")
    public R AddUser(@RequestBody UserAddReqVo userAddReqVo) {
        return sysUserService.addUser(userAddReqVo);
    }

    /**
     * 获取用户具有的角色信息，以及所有的角色信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/roles/{userId}")
    public R<UserRoleRespVo> getRoleMsg(@PathVariable String userId) {
        return roleService.getRoleMsg(userId);
    }
    //更新用户角色信息
    @PutMapping("/user/roles")
    public R updateMsg(@RequestBody UpdateMsgReqVo updateMsgReqVo) {
        return roleService.updateMsg(updateMsgReqVo);

    }
    //批量删除用户信息
    @DeleteMapping("/user")
    public R deleteByIds(@RequestBody List<Long> userIds) {
        return roleService.deleteByIds(userIds);
    }

}
