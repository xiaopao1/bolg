package com.yiming.blog.controller;

import com.yiming.blog.dao.dos.Archives;
import com.yiming.blog.service.ArticleService;
import com.yiming.blog.vo.ArticleVo;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//json
@Controller
@ResponseBody
//这两个可简化为@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        List<ArticleVo> articleVos = articleService.listArticlePage(pageParams);
        return Result.success(articleVos);
    }
    @PostMapping("hot")
    public Result listHotArticles(){
        List<ArticleVo> articleVos=articleService.listHotArticles();
        return Result.success(articleVos);
    }
    @PostMapping("new")
    public Result listNewArticles(){
        List<ArticleVo> articleVos=articleService.listNewArticles();
        return Result.success(articleVos);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        List<Archives> archivesList=articleService.listArchives();
        return Result.success(archivesList);
    }

}
