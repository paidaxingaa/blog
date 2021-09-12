package com.liyue.blog.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyue.blog.dao.CommentMapper;
import com.liyue.blog.entity.Comment;
import com.liyue.blog.service.CommentService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyue
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentBlogId(Long id) {
        List<Comment> comments = commentMapper.listCommentBlogId(id);
        return eachComment(comments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        Long parentId = comment.getParentComment().getId();
        if(parentId == 0){
            comment.setParentComment(null);
        }
        return commentMapper.saveComment(comment);
    }

    @Override
    public Page<Comment> listComment(Integer pageNum) {
        Page<Comment> comments = PageHelper.startPage(pageNum, 5).doSelectPage(() -> commentMapper.listComment());
        return comments;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentMapper.getCommentById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateComment(Comment comment, Long id) throws NotFoundException {
        comment.setUpdateTime(new Date());
        Comment c = commentMapper.getCommentById(id);
        if(c == null){
            throw new NotFoundException("没有该评论");
        }
        return commentMapper.updateComment(comment);
    }

    @Override
    public int deleteComment(Long id) throws NotFoundException {
        Comment c = commentMapper.getCommentById(id);
        if(c == null){
            throw new NotFoundException("没有该评论");
        }
        int result = commentMapper.deleteComment(id);
        return result;
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }

            //修改顶级节点的reply集合为迭代处理后的集合
            removeDuplicate(tempReplys);
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

    /**
     * 清除list中重复元素
     * @param list
     * @return
     */
    private List removeDuplicate(List list){
        for(int i = 0;i < list.size()-1;i++){
            for (int j = list.size()-1;j > i;j--){
                if (list.get(j).equals(list.get(i))){
                    list.remove(j);
                }
            }
        }
        return list;
    }
}
