package com.yiming.blog.service;

import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.LoginParams;

public interface LoginService {
    public Result login(LoginParams loginParams);
}
