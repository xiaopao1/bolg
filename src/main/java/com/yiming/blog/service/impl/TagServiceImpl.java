package com.yiming.blog.service.impl;

import com.yiming.blog.dao.mapper.TagMapper;
import com.yiming.blog.dao.pojo.Tag;
import com.yiming.blog.service.TagService;
import com.yiming.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatis-plus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public List<TagVo> hot(int limit) {
        //select tag_id from ms_article_tag group by tag_id order by count(1) desc limit 3
        List<Long> hotTagIds = tagMapper.findHotTagIds(limit);
        if(CollectionUtils.isEmpty(hotTagIds)){
            return Collections.emptyList();
        }
        //根据最热id，查询对应的Tag对象
        List<Tag> tags=tagMapper.findTagsByHotTagIds(hotTagIds);

        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> tags) {
        ArrayList<TagVo> tagVos = new ArrayList<>();
        for(Tag tag:tags){
            tagVos.add(copy(tag));
        }
        return tagVos;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
}
