package com.yiming.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.LoginService;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.utils.JWTUtils;
import com.yiming.blog.vo.ErrorCode;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    //JWT加redis实现登录功能
    /*
    token分为三部分 ABC
    A：Header，{"type":"JWT","alg":"HS256"} 固定
    B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
    C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。
    返回给前端的token只存入了用户id信息


    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,pwd);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
    1.用户传进来的account，password
    2.account不做处理，password对其加salt后取md5（此即为数据库存储的数据）
    3.select id,account,avatar,nikename from ms_sys_user where
    account = #{account} and password = #{pwd}
    4.没找到，即用户不存在。找到了，则根据id生成token返回，
    并以token为key，SysUser的信息转json作为value，即将用户信息存入redis中



    整个流程逻辑
    数据库存储的是用户名，password+salt后的MD5算法
     */

    private static final String slat = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(LoginParams loginParams) {

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //TODO
        System.out.println("用户端发送的密码为："+password);
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password) ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //对密码加salt取md5
        byte[] bytes = (password + slat).getBytes(StandardCharsets.UTF_8);
        String pwd = DigestUtils.md5DigestAsHex(bytes);
        //TODO
        System.out.println("加salt转换后的pwd："+pwd);
        SysUser sysUser = sysUserService.findUser(account, pwd);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        stringRedisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public Result logout(String token) {
        stringRedisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        //TODO temp
        System.out.println(loginParams.toString());
        //获取参数，并判断参数是否正常
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)
        || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //判断用户名是否被注册
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser !=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        //进行注册
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setNickname(nickname);
        //密码生成
        byte[] bytes = (password + slat).getBytes(StandardCharsets.UTF_8);
        String pwd = DigestUtils.md5DigestAsHex(bytes);
        //
        user.setPassword(pwd);
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        user.setAvatar("/static/img/logo.b3a48c0.png");
        user.setAdmin(1); //1 为true
        user.setDeleted(0); // 0 为false
        user.setSalt("");
        user.setStatus("");
        user.setEmail("");
        sysUserService.save(user);

        String token = JWTUtils.createToken(user.getId());
        stringRedisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(user),1, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> tokenBody = JWTUtils.checkToken(token);
        if (tokenBody == null) {
            return null;
        }
        String sysUserJson = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
        if(StringUtils.isBlank(sysUserJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(sysUserJson, SysUser.class);
        return sysUser;

    }

}
