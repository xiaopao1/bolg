package com.yiming.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiming.blog.dao.mapper.SysUserMapper;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.utils.JWTUtils;
import com.yiming.blog.vo.ErrorCode;
import com.yiming.blog.vo.LoginUserVo;
import com.yiming.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String pwd) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,pwd);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public Result getUserInfoByToken(String token) {
        Map<String, Object> tokenBody = JWTUtils.checkToken(token);
        if (tokenBody == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        String userJson = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
        SysUser user = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(user.getAccount());
        loginUserVo.setAvatar(user.getAvatar());
        loginUserVo.setId(user.getId());
        loginUserVo.setNickname(user.getNickname());


        return Result.success(loginUserVo);


    }
}
