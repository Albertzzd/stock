package com.itheima.stock.service;

import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.req.UserAddReqVo;
import com.itheima.stock.vo.req.UserReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.UserPage;

import java.util.Map;

/**
 * 登录
 */
public interface SysUserService {
    R<LoginRespVo> login(LoginReqVo vo);

    R<Map> getCaptcha();

    /**
     * 条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userReqVo
     * @return
     */
    R<PageResult<UserPage>> selectByUser(UserReqVo userReqVo);


    /**
     *添加用户信息
     * @param userAddReqVo
     * @return
     */
    R<String> addUser(UserAddReqVo userAddReqVo);
}
