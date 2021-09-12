package com.liyue.blog.service;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

/**
 * @author liyue
 */
public interface CommentService {

    /**
     * 根据blog_id查询所有comment
     * @return List<Comment>
     */
    public List<Comment> listCommentBlogId(Long id);

    /**
     * 添加comment
     * @param comment
     * @return int
     */
    public int saveComment(Comment comment);

    /**
     * 查询所有comment
     * @param pageNum
     * @return Page<Comment>
     */
    public Page<Comment> listComment(Integer pageNum);

    /**
     * 根据id查询comment
     * @param id
     * @return
     */
    public Comment getCommentById(Long id);

    /**
     * 更新comment
     * @param id
     * @param comment
     * @return int
     */
    public int updateComment(Comment comment,Long id) throws NotFoundException;

    /**
     * 删除comment
     * @param id
     * @return
     */
    public int deleteComment(@Param("id") Long id) throws NotFoundException;
}
