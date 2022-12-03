package com.yiming.blog.service.impl;

import com.yiming.blog.dao.mapper.CommentMapper;
import com.yiming.blog.dao.pojo.Comment;
import com.yiming.blog.dao.pojo.SysUser;
import com.yiming.blog.service.CommentService;
import com.yiming.blog.service.SysUserService;
import com.yiming.blog.utils.UserThreadLocal;
import com.yiming.blog.vo.CommentVo;
import com.yiming.blog.vo.Result;
import com.yiming.blog.vo.UserVo;
import com.yiming.blog.vo.params.CommentParams;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result getCommentsByArticleId(Long id) {
        /*      id          bigint auto_increment
                             primary key,
                content     varchar(255) collate utf8mb4_unicode_ci not null,
                create_date bigint                                  not null,
                article_id  int                                     not null,
                author_id   bigint                                  not null,
                parent_id   bigint                                  not null,
                to_uid      bigint                                  not null,
                level       varchar(1)                              not null*/
        //根据文章id查询相关信息，封装为CommentVo对象
        // 根据author_id查询UserVo
        //如果level为1，查询子评论  =>根据parent_id查询
        List<Comment> firstLeverComments = commentMapper.getFirstLeverComments(id);
        List<CommentVo> commentVos = copyList(firstLeverComments);

        return Result.success(commentVos);
    }

    @Override
    public Result createComment(CommentParams commentParams) {
        SysUser user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if(parent == null || parent == 0){
            comment.setLevel(1);
        } else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0:parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null? 0:toUserId);

        commentMapper.insert(comment);
        return Result.success(null);
    }


    private List<CommentVo> copyList(List<Comment> firstLeverComments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : firstLeverComments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setContent(comment.getContent());
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        Integer level = comment.getLevel();
        Long authorId = comment.getAuthorId();
        //根据author_id获得userVo  =>getUserVo(Long id)
        commentVo.setAuthor(getUserVo(authorId));
        //根据level获取子评论  =>getChildrens()
        //level为1时，才有子评论
        if(1==level) {
            Long id = comment.getId();
            List<CommentVo> commentVosList = getChildrens(id);
            commentVo.setChildrens(commentVosList);
        }
        //根据toUid获得userVo =>getUserVo(Long id)
        //level大于1时才有toUser
        if (level > 1) {
            commentVo.setToUser(getUserVo(comment.getToUid()));
        }
        return commentVo;
    }

    private UserVo getUserVo(Long authorId) {
        UserVo userVo = new UserVo();
        SysUser sysUser = sysUserService.findUserById(authorId);
        if(sysUser == null){
            userVo.setId(1L);
            userVo.setAvatar("/static/img/logo.b3a48c0.png");
            userVo.setNickname("yiming");
        }
        userVo.setId(sysUser.getId());
        userVo.setNickname(sysUser.getNickname());
        userVo.setAvatar(sysUser.getAvatar());
        return userVo;
    }

    private List<CommentVo> getChildrens(Long parentId) {
        List<Comment> childrenComments = commentMapper.getChildrenComments(parentId);
        return copyList(childrenComments);
    }


}