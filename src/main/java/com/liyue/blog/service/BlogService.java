package com.liyue.blog.service;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.BlogPage;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @author liyue
 */
public interface BlogService {
    /**
     * 根据id获取blog
     * @param id
     * @return Blog
     */
    public Blog getBlog(Long id);

    /**
     * 根据id获取blog并将MarkDown转换
     * @param id
     * @return Blog
     */
    public Blog getBlogAndConvert(Long id) throws NotFoundException;

    /**
     * 带条件分页查询所有blog
     * @param pageNum
     * @param blogPage
     * @return Page<Blog>
     */
    public Page<Blog> listBlog(Integer pageNum,BlogPage blogPage);

    /**
     * 前端分页查询已发布的blog
     * @param pageNum
     * @return Page<Blog>
     */
    public Page<Blog> listBlogWithPublished(Integer pageNum);

    /**
     * 根据type_id查询blog
     * @param id
     * @return Page<Blog>
     */
    public Page<Blog> listBlogByTypeId(Integer pageNum,Long id);

    /**
     * 按更新时间查询blog
     * @return List<Blog>
     */
    public List<Blog> listBlogByUpdateTime();

    /**
     * 根据条件查询blog
     * @param query
     * @param pageNum
     * @return Page<Blog>
     */
    public Page<Blog> listBlogByQuery(Integer pageNum,String query);

    /**
     * 根据tagId查找blog
     * @param id
     * @return Page<Blog>
     */
    public Page<Blog> listBlogByTagId(Integer pageNum,Long id);

    /**
     * 按年份归档查询blog
     * @return
     */
    public Map<String,List<Blog>> listByArchive();

    /**
     * 获取blog总数
     * @return
     */
    public Integer getBlogCount();

    /**
     * 添加blog
     * @param blog
     * @return int
     */
    public int saveBlog(Blog blog);

    /**
     * 更新指定blog
     * @param blog
     * @param id
     * @return int
     */
    public int updateBlog(Blog blog,Long id) throws NotFoundException;

    /**
     * 删除指定blog
     * @param id
     * @return int
     */
    public int deleteBlog(Long id) throws NotFoundException;
}
