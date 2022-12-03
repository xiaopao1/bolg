package com.yiming.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.vo.Result;

public interface BlogService {
    Result getCategory();
}
