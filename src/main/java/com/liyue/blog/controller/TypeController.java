package com.liyue.blog.controller;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.Type;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1")Integer pageNum,@PathVariable("id")Long id, Model model){
        //查询所有type
        List<Type> types = typeService.listType();
        model.addAttribute("types",types);
        //当从首页顶端点击分页时,默认选中第一个
        if (id == -1){
            id = types.get(0).getId();
        }
        //查询type对应的blog
        Page<Blog> page = blogService.listBlogByTypeId(pageNum, id);
        model.addAttribute("page",page);
        //返回选中的typeId
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
