package com.liyue.blog.controller;

import com.liyue.blog.entity.Blog;
import com.liyue.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author liyue
 */
@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        //按年份归档查询blog
        Map<String, List<Blog>> map = blogService.listByArchive();
        model.addAttribute("archiveMap",map);
        //查询已发布博客总数
        Integer count = blogService.getBlogCount();
        model.addAttribute("count",count);
        return "archives";
    }
}
