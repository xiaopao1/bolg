package com.yiming.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.LoginService;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.utils.JWTUtils;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    //JWT加redis实现登录功能
    /*
    token分为三部分 ABC
    A：Header，{"type":"JWT","alg":"HS256"} 固定
    B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
    C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。
    返回给前端的token只存入了用户id信息


    整个流程逻辑
    数据库存储的是用户名，password+salt后的MD5算法
     */

    private static final String slat = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(LoginParams loginParams) {

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //TODO
        System.out.println("用户端发送的密码为："+password);
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password) ){
            return Result.fail(999,"error");
        }
        byte[] bytes = (password + slat).getBytes(StandardCharsets.UTF_8);

        String pwd = DigestUtils.md5DigestAsHex(bytes);
        //TODO
        System.out.println("加salt转换后的pwd："+pwd);
        SysUser sysUser = sysUserService.findUser(account, pwd);
        if(sysUser == null){
            return Result.fail(999,"error");
        }
        String token = JWTUtils.createToken(sysUser.getId());
        stringRedisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }
}
