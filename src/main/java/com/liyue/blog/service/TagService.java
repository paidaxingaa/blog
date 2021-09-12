package com.liyue.blog.service;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Tag;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

/**
 * @author liyue
 */
public interface TagService {
    /**
     * 新增标签
     * @param tag
     */
    public int saveTag(Tag tag);

    /**
     * 获取指定标签
     * @param id
     * @return tag
     */
    public Tag getTag(Long id);

    /**
     * 根据名称获取标签
     * @param name
     * @return Tag
     */
    public Tag getTagByName(String name);

    /**
     * 分页查询所有标签
     * @param pageNum
     * @return Page<Tag>
     */
    public Page<Tag> listTag(Integer pageNum);

    /**
     * 查询所有标签
     * @return List<Tag>
     */
    public List<Tag> listTag();

    /**
     * 按blog数量排序查询tag
     * @return List<Tag>
     */
    public List<Tag> listTagSortByBlogNum();

    /**
     * 获取指定的tag
     * @param tagIds
     * @return List<Tag>
     */
    public List<Tag> listTag(String tagIds);

    /**
     * 删除指定标签
     * @param id
     */
    public int deleteTag(Long id) throws NotFoundException;

    /**
     * 更新指定标签
     * @param id Tag
     */
    public int updateTag(Long id,Tag tag) throws NotFoundException;
}
