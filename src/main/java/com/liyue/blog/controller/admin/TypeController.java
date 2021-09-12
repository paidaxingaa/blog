package com.liyue.blog.controller.admin;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Type;
import com.liyue.blog.service.TypeService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;

/**
 * @author liyue
 */
@Controller("AdminTypeController")
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String toTypesPage(@RequestParam(defaultValue = "1") Integer pageNum, Model model) {
        Page<Type> page = typeService.listType(pageNum);
        model.addAttribute("page", page);
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String toTypesInputPage(Model model) {
        model.addAttribute("type",new Type());
        return "/admin/types-input";
    }

    /**
     * 添加type
     * @param type
     * @param attributes
     * @param model
     * @return
     */
    @PostMapping("/types")
    public String saveType(Type type, RedirectAttributes attributes, Model model) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            model.addAttribute("message", "名称已存在");
            return "/admin/types-input";
        }
        int result = typeService.saveType(type);
        if (result == 1) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除type
     * @param id
     * @param attributes
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable("id") Long id, RedirectAttributes attributes) throws NotFoundException {
        int result = typeService.deleteType(id);
        if (result == 1) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String toTypeUpdatePage(@PathVariable("id") Long id, Model model) {
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    /**
     * 更新type
     * @param type
     * @param id
     * @param attributes
     * @param model
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/types/{id}")
    public String updateType(Type type, @PathVariable("id") Long id,
                             RedirectAttributes attributes, Model model) throws NotFoundException {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            model.addAttribute("message", "名称已存在");
            return "/admin/types-input";
        }
        int result = typeService.updateType(id, type);
        if (result == 1) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/types";
    }
}
