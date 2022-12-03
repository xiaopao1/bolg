package com.yiming.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiming.blog.dao.pojo.Comment;
import com.yiming.blog.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    //查询level为1的
    List<Comment> getFirstLeverComments(@Param("articleId") Long articleId);
    //查询level为2的
    List<Comment> getChildrenComments(@Param("parentId") Long parentId);

}
