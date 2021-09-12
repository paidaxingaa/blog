package com.liyue.blog.controller;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.Tag;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liyue
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String tags(@PathVariable("id")Long id, @RequestParam(defaultValue = "1")Integer pageNum, Model model){
        //查询所有tag
        List<Tag> tags = tagService.listTag();
        model.addAttribute("tags",tags);
        if (id == -1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.listBlogByTagId(pageNum, id);
        model.addAttribute("page",blogs);
        //返回选中的TagId
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
