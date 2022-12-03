package com.yiming.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yiming.blog.dao.mapper.CategoryMapper;
import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.service.BlogService;
import com.yiming.blog.service.CategoryService;
import com.yiming.blog.vo.CategoryVo;
import com.yiming.blog.vo.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BlogServiceImpl implements BlogService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获得所有标签
     *
     * @return
     */
    @Override
    public Result getCategory() {
        List<Category> categoryList = categoryMapper.selectList(null);

        ArrayList<CategoryVo> categoryVos = new ArrayList<CategoryVo>();
        for (Category category : categoryList) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setAvatar(category.getAvatar());
            categoryVo.setAvatar(category.getAvatar());
            categoryVos.add(categoryVo);
        }

        return Result.success(categoryVos);
    }
}
