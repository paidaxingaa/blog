package com.liyue.blog.controller.admin;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.*;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.service.TagService;
import com.liyue.blog.service.TypeService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author woyigeren
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Value("/image/")
    private String relativePath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("/blogs")
    public String toBlogPage(@RequestParam(defaultValue = "1") Integer pageNum, Model model,BlogPage blogPage){
        List<Type> types = typeService.listType();
        model.addAttribute("types",types);
        Page<Blog> blogs = blogService.listBlog(pageNum,blogPage);
        model.addAttribute("page",blogs);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String searchBlog(@RequestParam(defaultValue = "1") Integer pageNum, BlogPage blogPage, Model model){
        Page<Blog> blogs = blogService.listBlog(pageNum, blogPage);
        model.addAttribute("page",blogs);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String toBlogInputPage(Model model){
        //????????????
        List<Type> types = typeService.listType();
        model.addAttribute("types",types);
        //????????????
        List<Tag> tags = tagService.listTag();
        model.addAttribute("tags",tags);
        //????????????????????????????????????blog??????
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    /**
     * ??????blog
     * @param blog
     * @return
     */
    @PostMapping("/blogs")
    public String saveBlog(Blog blog, HttpSession session, RedirectAttributes attributes,@RequestParam("tagIds") String tagIds){
        //?????????????????????
        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagService.listTag(tagIds));
        //??????
        int result = blogService.saveBlog(blog);
        if (result == 1){
            attributes.addFlashAttribute("message","????????????");
        }else {
            attributes.addFlashAttribute("message","????????????");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input")
    public String toUpdateBlogPage(@PathVariable("id") Long id,Model model){
        Blog originalBlog = blogService.getBlog(id);
        model.addAttribute("blog",originalBlog);
        //??????tagIds
        if(!originalBlog.getTags().isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag : originalBlog.getTags()){
                if (flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            model.addAttribute("tagIds",ids.toString());
        }
        //????????????
        List<Type> types = typeService.listType();
        model.addAttribute("types",types);
        //????????????
        List<Tag> tags = tagService.listTag();
        model.addAttribute("tags",tags);
        return "admin/blogs-input";
    }

    /**
     * ??????blog
     * @param blog
     * @param attributes
     * @param id
     * @param tagIds
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/blogs/{id}")
    public String updateBlog(Blog blog,RedirectAttributes attributes,@PathVariable("id") Long id,@RequestParam("tagIds") String tagIds) throws NotFoundException {
        //????????????
        blog.setTags(tagService.listTag(tagIds));
        //??????
        int result = blogService.updateBlog(blog, id);
        if (result == 1){
            attributes.addFlashAttribute("message","????????????");
        }else {
            attributes.addFlashAttribute("message","????????????");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * ??????blog
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable("id") Long id,RedirectAttributes attributes) throws NotFoundException {
        int result = blogService.deleteBlog(id);
        if (result == 1){
            attributes.addFlashAttribute("message","????????????");
        }else {
            attributes.addFlashAttribute("message","????????????");
        }
        return "redirect:/admin/blogs";
    }


    /**
     * ????????????
     * @param file
     * @return Map<String,Object>
     */
    @ResponseBody
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("editormd-image-file") MultipartFile file,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if (file.getSize()==0){
            map.put("msg","???????????????");
        }
        //??????img???????????????
        String realPath = "E:\\CourseDesign\\img";
        //?????????????????????
        String originalName = file.getOriginalFilename();
        //??????????????????
        String fileName = UUID.randomUUID()+originalName;
        //?????????????????????File???????????????????????????????????????????????????????????????????????????
        File targetFile = new File(realPath,fileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("success",1);
        map.put("message","??????????????????");
        map.put("url",getItemPath(request)+contextPath+relativePath+fileName);
        return map;
    }

    public static String getItemPath(HttpServletRequest request){
        String scheme = request.getScheme(); // ?????????????????????http
        String serverName = request.getServerName(); // ????????????????????? localhost
        int serverPort = request.getServerPort(); // ???????????? 8080
        String path = scheme+"://"+serverName+":"+serverPort;
        return path;
    }

//    @ResponseBody
//    @GetMapping("/blog/{id}")
//    public Map<String,Object> listById(@PathVariable("id")Long id){
//        Map<String,Object> map = new HashMap<>();
//        Blog blog = blogService.getBlog(id);
//        map.put("blog",blog);
//        return map;
//    }
}
