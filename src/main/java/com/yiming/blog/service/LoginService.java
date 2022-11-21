package com.yiming.blog.service;

import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.LoginParams;
import org.springframework.stereotype.Service;


public interface LoginService {
    public Result login(LoginParams loginParams);

    Result logout(String token);
}
