package com.yiming.blog.service;

import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    /**
     * 根据文章ID找标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 找出最热的标签（即对应文章最多）
     * @param limit
     * @return
     */
    List<TagVo> hot(int limit);

    /**
     * 获取所有标签
     * @return
     */
    Result getAllTags();
}
