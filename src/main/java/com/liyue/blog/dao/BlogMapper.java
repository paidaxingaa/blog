package com.liyue.blog.dao;


import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.BlogPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface BlogMapper {
    /**
     * 根据id获取blog
     * @param id
     * @return Blog
     */
    public Blog getBlog(@Param("id") Long id);


    /**
     * 后台根据条件分页查询所有blog
     * @return
     * @param blogPage
     */
    public List<Blog> listBlog(BlogPage blogPage);

    /**
     * 前端查询已发布所有的blog
     * @return List<Blog>
     */
    public List<Blog> listBlogWithPublished();

    /**
     * 前端查询已发布并是推荐所有的blog
     * @return List<Blog>
     */
    public List<Blog> listBlogWithPublishedAndRecommended();

    /**
     * 根据type_id查询blog
     * @param id
     * @return List<Blog>
     */
    public List<Blog> listBlogByTypeId(@Param("type_id")Long id);

    /**
     * 根据条件查询blog
     * @param query
     * @return List<Blog>
     */
    public List<Blog> listBlogByQuery(@Param("query") String query);

    /**
     * 根据多个id查找blog
     * @param list
     * @return List<Blog>
     */
    public List<Blog> listBlogByIdList(@Param("list") List<Long> list);

    /**
     * 添加blog
     * @param blog
     * @return int
     */
    public int saveBlog(Blog blog);

    /**
     * 更新指定blog
     * @param blog
     * @return int
     */
    public int updateBlog(Blog blog);

    /**
     * 更新访问次数
     * @param id
     * @return int
     */
    public int updateViews(@Param("id")Long id);

    /**
     * 删除指定blog
     * @param id
     * @return int
     */
    public int deleteBlog(@Param("id")Long id);

    /**
     * 获取所有年份
     * @return List<String>
     */
    public List<String> getYears();

    /**
     * 根据年份查询blog
     * @return List<Blog>
     */
    public List<Blog> listBlogByYear(@Param("year") String year);

    /**
     * 查询已发布的博客总数
     * @return Integer
     */
    public Integer getBlogCount();

}
