package com.yiming.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
@Data
public class CommentVo {
    // id of the comment
    //防止前端精度损失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    // 用户信息
    private UserVo author;
    // 评论内容
    private String content;
    // 子评论
    private List<CommentVo> childrens;
    // 时间
    private String createDate;
    // 评论层级
    private Integer level;
    // 评论给谁
    private UserVo toUser;
}
