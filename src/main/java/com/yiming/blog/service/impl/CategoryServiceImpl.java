package com.yiming.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiming.blog.dao.mapper.CategoryMapper;
import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.service.CategoryService;
import com.yiming.blog.vo.CategoryVo;
import com.yiming.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
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

    @Override
    public Result getCategorys() {
        List<Category> categoryList = categoryMapper.selectList(null);
//        List<Category> list = categoryMapper.list();


        ArrayList<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setCategoryName(category.getCategoryName());
            categoryVo.setAvatar(category.getAvatar());
            categoryVos.add(categoryVo);
        }

        return Result.success(categoryVos);
    }
}
