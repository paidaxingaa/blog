package com.liyue.blog.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyue.blog.dao.TagMapper;
import com.liyue.blog.entity.Tag;
import com.liyue.blog.service.TagService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyue
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveTag(Tag tag){
        tag.setCreateTime(new Date());
        int result = tagMapper.saveTag(tag);
        return result;
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public Page<Tag> listTag(Integer pageNum) {
        Page<Tag> page = PageHelper.startPage(pageNum, 3).doSelectPage(() -> tagMapper.listTag());
        return page;
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> listTagSortByBlogNum() {
        PageHelper.startPage(1,6,"id asc");
        List<Tag> tags = tagMapper.listTag();
        return tags;
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        List<Long> list = new ArrayList<>();
        if(tagIds != null && !"".equals(tagIds)){
            String [] strings = tagIds.split(",");
            for (int i=0;i < strings.length;i++){
                list.add(Long.valueOf(strings[i]));
            }
        }
        List<Tag> tags = tagMapper.listTagByListId(list);
        return tags;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTag(Long id) throws NotFoundException {
        Tag t = tagMapper.getTag(id);
        if ( t == null){
            throw new NotFoundException("不存在该分类");
        }
        return tagMapper.deleteTag(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTag(Long id,Tag tag) throws NotFoundException{
        Tag t = tagMapper.getTag(id);
        if ( t == null){
            throw new NotFoundException("不存在该分类");
        }
        tag.setUpdateTime(new Date());
        return tagMapper.updateTag(tag);
    }
}
