package com.yiming.blog.service;

import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.params.CommentParams;

public interface CommentService {
    Result getCommentsByArticleId(Long id);

    Result createComment(CommentParams commentParams);
}
