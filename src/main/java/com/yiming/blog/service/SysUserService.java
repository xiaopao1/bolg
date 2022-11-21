package com.yiming.blog.service;

import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String pwd);

    Result getUserInfoByToken(String token);
}
