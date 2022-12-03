package com.yiming.blog.controller;

import com.yiming.blog.service.CommentService;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentsService;

    @GetMapping("article/{id}")
    public Result getCommentsByArticleId(@PathVariable("id") Long id) {
        return commentsService.getCommentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result createComment(@RequestBody CommentParams commentParams) {
        return commentsService.createComment(commentParams);
    }
}
