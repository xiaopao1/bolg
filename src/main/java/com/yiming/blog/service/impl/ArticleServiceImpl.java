package com.yiming.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiming.blog.dao.dos.Archives;
import com.yiming.blog.dao.mapper.ArticleMapper;
import com.yiming.blog.dao.pojo.Article;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.ArticleService;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.service.TagService;
import com.yiming.blog.vo.ArticleVo;
import com.yiming.blog.vo.TagVo;
import com.yiming.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    private ArticleVo copy(Article article, boolean isAuthor, boolean isBody, boolean isTags) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd:HH:mm"));
        //并不是所有的接口，都需要标签，作者信息
        if(isAuthor){
            Long authorId = article.getAuthorId();
            SysUser sysuser = sysUserService.findUserById(authorId);
            if (sysuser == null) {
                sysuser = new SysUser();
                sysuser.setNickname("yiming");
            }
            articleVo.setAuthor(sysuser.getNickname());
        }
        if(isBody){
//            articleVo.
        }
        if(isTags){
            Long articleId = article.getId();
            List<TagVo> tagVos = tagService.findTagsByArticleId(articleId);

            articleVo.setTags(tagVos);
        }
        return articleVo;
    }
    private List<ArticleVo> copyList(List<Article> articleList,boolean isAuthor,
                                     boolean isBody,boolean isTags){
        ArrayList<ArticleVo> articleVoList = new ArrayList<>();
        for(Article article:articleList){
            ArticleVo copy = copy(article, isAuthor, isBody, isTags);
            articleVoList.add(copy);
        }
        return articleVoList;
    }



    @Override
    public List<ArticleVo> listArticlePage(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        Page<Article> articlePage = articleMapper.selectPage(page, LambdaQueryWrapper);
        //mybatis-plus插件的getRecords才是真正数据
        List<Article> articleList = articlePage.getRecords();
        //需将articleList转为articlevoList
        List<ArticleVo> articleVoList = copyList(articleList, true, false, true);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> listHotArticles() {
        //select id,title from ms_article order by view_counts desc limit 3;
        int limit = 5;
//        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        return copyList(articles,false,false,false);

    }

    @Override
    public List<ArticleVo> listNewArticles() {

        int limit = 5;
//        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        lambdaQueryWrapper.select(Article::getId,Article::getTitle);
        lambdaQueryWrapper.last("limit "+limit);
        //select id,title from ms_article order by CreateDate desc limit 3;
        List<Article> articles = articleMapper.selectList(lambdaQueryWrapper);
        return copyList(articles,false,false,false);
    }

    @Override
    public List<Archives> listArchives() {
//        List<Archives>  archivesList=articleMapper.listArchives();

        List<Archives> archivesList = articleMapper.listArchives();
        return archivesList;
    }
}
