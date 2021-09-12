package com.liyue.blog.dao;

import com.liyue.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface TagMapper {
    /**
     * 新增标签
     * @param tag
     * @return int
     */
    public int saveTag(Tag tag);

    /**
     * 获取指定标签
     * @param id
     * @return Tag
     */
    public Tag getTag(@Param("id") Long id);

    /**
     * 根据名称获取指定标签
     * @param name
     * @return Tag
     */
    public Tag getTagByName(@Param("name")String name);

    /**
     * 查询所有标签
     * @return List<Tag>
     */
    public List<Tag> listTag();

    /**
     * 根据多个id获取tag
     * @param list
     * @return List<Tag>
     */
    public List<Tag> listTagByListId(List<Long> list);

    /**
     * 删除指定标签
     * @param id
     * @return int
     */
    public int deleteTag(@Param("id") Long id);

    /**
     * 更新指定标签
     * @param tag
     * @return int
     */
    public int updateTag(Tag tag);

}
