package com.yiming.blog.controller;

import com.yiming.blog.service.BlogService;
import com.yiming.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("list")
    public Result getCategorys() {
        return blogService.getCategory();
    }
}
