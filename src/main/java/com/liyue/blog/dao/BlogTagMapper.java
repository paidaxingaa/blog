package com.liyue.blog.dao;

import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface BlogTagMapper {
    /**
     * 根据blog_id查询所有Tag
     * @param id
     * @return
     */
    public List<Tag> getTagByBlogId(@Param("blog_id") Long id);

    /**
     * 根据tag_id查询所有Blog
     * @param id
     * @return
     */
    public List<Blog> getBlogByTagId(@Param("tag_id") Long id);

    /**
     * 添加博客时保存对应的标签
     * @param list
     * @param id
     * @return
     */
    public int saveBlogWithTagId(@Param("list") List<Tag> list,@Param("blog_id") Long id);

    /**
     * 根据指定blog_id删除tag
     * @param id
     * @return
     */
    public int deleteTagByBlogId(@Param("blog_id") Long id);
}
