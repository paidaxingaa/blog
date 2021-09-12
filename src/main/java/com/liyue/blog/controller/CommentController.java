package com.liyue.blog.controller;

import com.liyue.blog.entity.Blog;
import com.liyue.blog.entity.Comment;
import com.liyue.blog.entity.User;
import com.liyue.blog.service.BlogService;
import com.liyue.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author liyue
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long id, Model model){
        List<Comment> comments = commentService.listCommentBlogId(id);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String saveComment(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar("/images/avatar.jpg");
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;
    }
}
