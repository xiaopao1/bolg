package com.yiming.blog.controller;

import com.yiming.blog.service.TagService;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping
    public Result getAllTags(){
        return tagService.getAllTags();
    }
    @GetMapping("hot")
    public Result listHotTags(){
        int limit = 6;
        List<TagVo> tagVos= tagService.hot(limit);
        return Result.success(tagVos);
    }

}
