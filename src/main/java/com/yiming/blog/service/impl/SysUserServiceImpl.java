package com.yiming.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiming.blog.dao.mapper.SysUserMapper;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.LoginService;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.utils.JWTUtils;
import com.yiming.blog.vo.ErrorCode;
import com.yiming.blog.vo.LoginUserVo;
import com.yiming.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private LoginService loginService;
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
        //1.token合法性检查
        //2.看redis是否能成功解析
        SysUser user = loginService.checkToken(token);
        if(user==null){
            return Result.fail(ErrorCode.TOKEN_ILLEGAL.getCode(),ErrorCode.TOKEN_ILLEGAL.getMsg());
        }


        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(user.getAccount());
        loginUserVo.setAvatar(user.getAvatar());
        loginUserVo.setId(user.getId());
        loginUserVo.setNickname(user.getNickname());


        return Result.success(loginUserVo);


    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.last("limit 1");
        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public void save(SysUser user) {
        sysUserMapper.insert(user);
    }
}
