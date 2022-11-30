package com.yiming.blog.service;

import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.LoginParams;
import org.springframework.stereotype.Service;


public interface LoginService {
    /**
     * 登录
     * @param loginParams
     * @return
     */
    public Result login(LoginParams loginParams);

    /**
     * 登出
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);

    /**
     * 从token中检查是否有用户信息
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
