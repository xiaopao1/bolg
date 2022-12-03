package com.yiming.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiming.blog.dao.mapper.ArticleMapper;
import com.yiming.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateViewConut(ArticleMapper articleMapper, Article article){


        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts()+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        //乐观锁，先查询一次，修改时再查询一次，如果没有被修改再执行加1，保证多线程下单的安全问题
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        //update article set view_count=100 where view_count=99 and article_id = ???
        articleMapper.update(articleUpdate,queryWrapper);
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/



    }
}
