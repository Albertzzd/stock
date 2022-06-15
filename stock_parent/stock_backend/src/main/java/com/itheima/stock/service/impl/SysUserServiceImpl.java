package com.itheima.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.itheima.stock.mapper.SysUserMapper;
import com.itheima.stock.pojo.SysPermission;
import com.itheima.stock.pojo.SysUser;
import com.itheima.stock.service.PermissionService;
import com.itheima.stock.service.SysUserService;
import com.itheima.stock.utils.IdWorker;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.req.UserAddReqVo;
import com.itheima.stock.vo.req.UserReqVo;
import com.itheima.stock.vo.resp.*;
import com.mysql.jdbc.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 登录
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IdWorker idWorker;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private PermissionService permissionService;

    /**
     * 登录
     *
     * @param vo
     * @return
     */
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        if (vo == null || Strings.isNullOrEmpty(vo.getUsername()) || Strings.isNullOrEmpty(vo.getPassword())) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        if (Strings.isNullOrEmpty(vo.getCode())) {
            return R.error(ResponseCode.SYSTEM_VERIFY_CODE_NOT_EMPTY.getMessage());
        }
        String code = (String) redisTemplate.opsForValue().get(vo.getRkey());
        if (Strings.isNullOrEmpty(code) || !code.equals(vo.getCode())) {
            return R.error(ResponseCode.SYSTEM_VERIFY_CODE_ERROR.getMessage());
        }
        redisTemplate.delete(vo.getRkey());
        SysUser user = sysUserMapper.queryUserByUserName(vo.getUsername());
        if (user == null) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        boolean matches = passwordEncoder.matches(vo.getPassword(), user.getPassword());
        if (!matches) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }
        LoginRespVo resp = new LoginRespVo();
        BeanUtils.copyProperties(user, resp);
        //TODO
        //获取指定用户的权限集合
        List<SysPermission> permissions = permissionService.getPermissionByUserId(user.getId());

        //获取树状权限菜单数据
        List<PermissionRespNodeVo> tree = permissionService.getTree(permissions, "0", true);

        //获取菜单按钮集合
        List<String> authBtnPerms = permissions.stream()
                .filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
                .map(per -> per.getCode()).collect(Collectors.toList());
        resp.setMenus(tree);
        resp.setPermissions(authBtnPerms);

        return R.ok(ResponseCode.SUCCESS.getMessage(), resp);
    }

    /**
     * 验证码
     *
     * @return
     */
    @Override
    public R<Map> getCaptcha() {
        String code = RandomStringUtils.randomNumeric(4);

        String rkey ="ck:"+ idWorker.nextId();

        redisTemplate.opsForValue().set(rkey, code, 60, TimeUnit.SECONDS);

        Map<String, String> map = new HashMap<>();
        map.put("rkey", rkey);
        map.put("code", code);
        return R.ok(map);

    }

    /**
     * 条件综合查询用户分页信息，条件包含：分页信息 用户创建日期范围
     * @param userReqVo
     * @return
     */
    @Override
    public R<PageResult<UserPage>> selectByUser(UserReqVo userReqVo) {
        PageHelper.startPage(userReqVo.getPageNum(),userReqVo.getPageSize());
        List<UserPage> users = sysUserMapper.selectByPageAllUser(userReqVo.getUsername(), userReqVo.getNickname(),
                userReqVo.getStartTime(), userReqVo.getEndTime());
        PageInfo<UserPage> pageInfo = new PageInfo<>(users);
        PageResult<UserPage> pageResult = new PageResult<>(pageInfo);

        return R.ok(pageResult);
    }


    /**
     * 添加用户
     *
     * @param userAddReqVo
     * @return
     */

    @Override
    public R<String> addUser(UserAddReqVo userAddReqVo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userAddReqVo, user);
        user.setId(idWorker.nextId() + "");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int count = sysUserMapper.insert(user);
        if (count == 0) {
            return R.error(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());

    }
}
