package com.yiming.blog.service;

import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String pwd);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result getUserInfoByToken(String token);
    SysUser findUserByAccount(String account);

    void save(SysUser user);
}
