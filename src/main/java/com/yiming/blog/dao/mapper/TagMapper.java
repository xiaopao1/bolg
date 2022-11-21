package com.yiming.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiming.blog.dao.pojo.Tag;
import com.yiming.blog.vo.TagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签id
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    /**
     * 根据最热id查询对应对象
     * @param hotTagIds
     * @return
     */
    List<Tag> findTagsByHotTagIds(@Param("TagIds") List<Long> hotTagIds);
}
