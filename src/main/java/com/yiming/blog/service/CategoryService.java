package com.yiming.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.vo.CategoryVo;
import com.yiming.blog.vo.Result;

public interface CategoryService extends IService<Category> {
    CategoryVo findCategoryById(Long categoryId);

    Result getCategorys();
}
