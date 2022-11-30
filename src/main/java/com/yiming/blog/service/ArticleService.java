package com.yiming.blog.service;

import com.yiming.blog.dao.dos.Archives;
import com.yiming.blog.vo.ArticleVo;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    /**
     * 分页查询
     * @param pageParams
     * @return
     */
     List<ArticleVo> listArticlePage(PageParams pageParams);

    /**
     * 查询最热文章
     * @return
     */
    List<ArticleVo> listHotArticles();
    /**
     * 查询最新文章
     * @return
     */
    List<ArticleVo> listNewArticles();

    /**
     * 文章归档
     * @return
     */
    List<Archives> listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);
}
