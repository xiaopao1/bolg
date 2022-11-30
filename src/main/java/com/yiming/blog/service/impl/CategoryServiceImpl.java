package com.yiming.blog.service.impl;

import com.yiming.blog.dao.mapper.CategoryMapper;
import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.service.CategoryService;
import com.yiming.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据categoryId查询CategoryVo对象
     * @param categoryId
     * @return
     */
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
