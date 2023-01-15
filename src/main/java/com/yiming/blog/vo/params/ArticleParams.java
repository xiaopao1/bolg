package com.yiming.blog.vo.params;

import com.yiming.blog.dao.pojo.Category;
import com.yiming.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {
    private String title;

    private Long id;

    private ArticleBodyParams body;

    private Category category;

    private String summary;

    private List<TagVo> tags;


}
