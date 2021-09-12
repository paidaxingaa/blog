package com.liyue.blog.controller.admin;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Tag;
import com.liyue.blog.service.TagService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyue
 */
@Controller("AdminTagController")
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String toTagsPage(@RequestParam(defaultValue = "1") Integer pageNum, Model model) {
        Page<Tag> page = tagService.listTag(pageNum);
        model.addAttribute("page", page);
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String toTagsInputPage(Model model) {
        model.addAttribute("tag",new Tag());
        return "/admin/tags-input";
    }

    /**
     * 添加tag
     * @param tag
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/tags")
    public String saveTag(Tag tag, RedirectAttributes attributes, Model model) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            model.addAttribute("message", "名称已存在");
            return "/admin/tags-input";
        }
        int result = tagService.saveTag(tag);
        if (result == 1) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除tag
     * @param id
     * @param attributes
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable("id") Long id, RedirectAttributes attributes) throws NotFoundException {
        int result = tagService.deleteTag(id);
        if (result == 1) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String toTagUpdatePage(@PathVariable("id") Long id, Model model) {
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    /**
     * 更新tag
     * @param tag
     * @param id
     * @param attributes
     * @param model
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/tags/{id}")
    public String updateTag(Tag tag, @PathVariable("id") Long id,
                            RedirectAttributes attributes, Model model) throws NotFoundException {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            model.addAttribute("message", "名称已存在");
            return "/admin/tags-input";
        }
        int result = tagService.updateTag(id, tag);
        if (result == 1) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/tags";
    }

//    @ResponseBody
//    @GetMapping("listTag")
//    public Map<String,Object> list(@RequestParam(defaultValue = "1")Integer pageNum){
//        Map<String,Object> map = new HashMap<>();
//        Page<Tag> tags = tagService.listTag(pageNum);
//        map.put("tags",tags);
//        return map;
//    }
}
