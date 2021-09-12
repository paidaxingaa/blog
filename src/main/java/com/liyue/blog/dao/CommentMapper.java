package com.liyue.blog.dao;

import com.liyue.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface CommentMapper {

    /**
     * 根据blog_id查询所有comment
     * @param id
     * @return List<Comment>
     */
    public List<Comment> listCommentBlogId(@Param("blog_id")Long id);

    /**
     * 添加comment
     * @param comment
     * @return int
     */
    public int saveComment(Comment comment);

    /**
     * 根据parentId查找所有comment
     * @param id
     * @return List<Comment>
     */
    public List<Comment> listCommentByParentId(@Param("parent_id") Long id);

    /**
     * 查询所有comment
     * @return List<Comment>
     */
    public List<Comment> listComment();

    /**
     * 根据blogId查找所有comment
     * @param id
     * @return List<Comment>
     */
    public List<Comment> listCommentByBlogId(@Param("blog_id") Long id);

    /**
     * 根据id查找comment
     * @param id
     * @return Comment
     */
    public Comment getCommentById(@Param("id")Long id);

    /**
     * 更新comment
     * @param comment
     * @return int
     */
    public int updateComment(Comment comment);

    /**
     * 删除comment
     * @param id
     * @return
     */
    public int deleteComment(@Param("id") Long id);
}
