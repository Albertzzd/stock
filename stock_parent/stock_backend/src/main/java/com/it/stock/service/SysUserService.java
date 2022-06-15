package com.it.stock.service;

import com.it.stock.vo.req.LoginReqVo;
import com.it.stock.vo.req.UserAddReqVo;
import com.it.stock.vo.req.UserReqVo;
import com.it.stock.vo.resp.PageResult;
import com.it.stock.vo.resp.R;
import com.it.stock.vo.resp.UserPage;
import com.it.stock.vo.resp.LoginRespVo;

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
