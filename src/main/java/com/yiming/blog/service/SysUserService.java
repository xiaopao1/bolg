package com.yiming.blog.service;

import com.yiming.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String pwd);
}
