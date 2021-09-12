package com.liyue.blog.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyue.blog.dao.BlogMapper;
import com.liyue.blog.dao.BlogTagMapper;
import com.liyue.blog.dao.TagMapper;
import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.BlogPage;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.utils.MarkdownUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liyue
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog getBlogAndConvert(Long id) throws NotFoundException {
        Blog blog = blogMapper.getBlog(id);
        if (blog == null) {
            throw new NotFoundException("不存在该博客");
        }
        String content = blog.getContent();
        String convert = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(convert);
        blogMapper.updateViews(id);
        return blog;
    }


    @Override
    public Page<Blog> listBlog(Integer pageNum, BlogPage blogPage) {
        Page<Blog> page = PageHelper.startPage(pageNum, 6).doSelectPage(()->blogMapper.listBlog(blogPage));
        return page;
    }

    @Override
    public Page<Blog> listBlogWithPublished(Integer pageNum) {
        Page<Blog> page = PageHelper.startPage(pageNum, 5).doSelectPage(()->blogMapper.listBlogWithPublished());
        return page;
    }

    @Override
    public Page<Blog> listBlogByTypeId(Integer pageNum,Long id) {
        Page<Blog> page = PageHelper.startPage(pageNum, 5).doSelectPage(() -> blogMapper.listBlogByTypeId(id));
        return page;
    }

    @Override
    public List<Blog> listBlogByUpdateTime() {
        PageHelper.startPage(1,6,"b.update_time desc");
        List<Blog> blogs = blogMapper.listBlogWithPublishedAndRecommended();
        return blogs;
    }

    @Override
    public Page<Blog> listBlogByQuery(Integer pageNum,String query) {
        Page<Blog> page = PageHelper.startPage(pageNum, 4).doSelectPage(()->blogMapper.listBlogByQuery(query));
        return page;
    }

    @Override
    public Page<Blog> listBlogByTagId(Integer pageNum,Long id) {
        List<Blog> blogList = blogTagMapper.getBlogByTagId(id);
        if (blogList.isEmpty()){
            return new Page<Blog>();
        }
        List<Long> idList = new ArrayList<>();
        for(Blog blog : blogList){
            idList.add(blog.getId());
        }
        Page<Blog> page = PageHelper.startPage(pageNum, 5).doSelectPage(()->blogMapper.listBlogByIdList(idList));
        return page;
    }

    @Override
    public Map<String, List<Blog>> listByArchive() {
        Map<String,List<Blog>> map = new HashMap<>();
        //获取年份
        List<String> years = blogMapper.getYears();
        for (String year : years){
            List<Blog> blogs = blogMapper.listBlogByYear(year);
            map.put(year,blogs);
        }
        return map;
    }

    @Override
    public Integer getBlogCount() {
        return blogMapper.getBlogCount();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        int result = blogMapper.saveBlog(blog);
        //为对应blog添加标签
        blogTagMapper.saveBlogWithTagId(blog.getTags(),blog.getId());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateBlog(Blog blog, Long id) throws NotFoundException {
        Blog b = blogMapper.getBlog(id);
        if (b == null){
            throw new NotFoundException("不存在该blog");
        }

        blog.setUser(b.getUser());
        blog.setViews(b.getViews());
        blog.setUpdateTime(new Date());

        blogTagMapper.deleteTagByBlogId(id);
        blogTagMapper.saveBlogWithTagId(blog.getTags(),id);
        return blogMapper.updateBlog(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBlog(Long id) throws NotFoundException {
        Blog blog = blogMapper.getBlog(id);
        if (blog == null){
            throw new NotFoundException("不存在该blog");
        }
        return blogMapper.deleteBlog(id);
    }
}
