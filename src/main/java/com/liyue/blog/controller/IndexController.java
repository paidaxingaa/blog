package com.liyue.blog.controller;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.Tag;
import com.liyue.blog.entity.Type;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.service.TagService;
import com.liyue.blog.service.TypeService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liyue
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String toIndexPage(@RequestParam(defaultValue = "1")Integer pageNum, Model model){
        //分页查询blog
        Page<Blog> blogs = blogService.listBlogWithPublished(pageNum);
        model.addAttribute("page",blogs);
        //查询type
        List<Type> types = typeService.listTypeSortByBlogNum();
        model.addAttribute("types",types);
        //查询tag
        List<Tag> tags = tagService.listTagSortByBlogNum();
        model.addAttribute("tags",tags);
        //按更新时间查询blog
        List<Blog> recommendBlogs = blogService.listBlogByUpdateTime();
        model.addAttribute("recommendBlogs",recommendBlogs);
        return "index";
    }

    /**
     * 查询
     * @param pageNum
     * @param query
     * @param model
     * @return
     */
    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(value = "query",required = false) String query, Model model){
        Page<Blog> page = blogService.listBlogByQuery(pageNum, query);
        model.addAttribute("page",page);
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 博客详情
     * @param id
     * @param model
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable("id") Long id,Model model) throws NotFoundException {
        Blog blog = blogService.getBlogAndConvert(id);
        model.addAttribute("blog",blog);
        return "blog";
    }
}
