package com.liyue.blog.controller.admin;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Comment;
import com.liyue.blog.service.CommentService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author liyue
 */
@Controller("AdminCommentController")
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String toCommentsPage(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        Page<Comment> page = commentService.listComment(pageNum);
        model.addAttribute("page",page);
        return "admin/comments";
    }

    @GetMapping("/comments/input")
    public String toCommentInputPage(Model model){
        model.addAttribute("comment",new Comment());
        return "admin/comments-input";
    }

//    /**
//     * 添加comment
//     * @param comment
//     * @param attributes
//     * @return
//     */
//    @PostMapping("/comments")
//    public String saveComment(Comment comment, RedirectAttributes attributes){
//        int result = commentService.saveComment(comment);
//        if (result == 1){
//            attributes.addFlashAttribute("message","添加成功");
//        }else {
//            attributes.addFlashAttribute("message","添加失败");
//        }
//        return "redirect:/admin/comments";
//    }

    @GetMapping("/comments/{id}/input")
    public String toCommentUpdatePage(@PathVariable("id")Long id, Model model){
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment",comment);
        return "admin/comments-input";
    }

    @PostMapping("/comments/{id}")
    public String updateComment(Comment comment,@PathVariable("id")Long id,RedirectAttributes attributes) throws NotFoundException {
        int result = commentService.updateComment(comment, id);
        if (result == 1){
            attributes.addFlashAttribute("message","更新成功");
        }else {
            attributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/comments";
    }

    @GetMapping("/comments/{id}/delete")
    public String deleteComment(@PathVariable("id") Long id,RedirectAttributes attributes) throws NotFoundException {
        int result = commentService.deleteComment(id);
        if (result == 1){
            attributes.addFlashAttribute("message","删除成功");
        }else{
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/comments";
    }
}
