package com.liyue.blog.service;

import com.github.pagehelper.Page;
import com.liyue.blog.entity.Type;
import org.apache.ibatis.javassist.NotFoundException;

import java.text.ParseException;
import java.util.List;

/**
 * @author liyue
 */
public interface TypeService {
    /**
     * 新增分类
     * @param type
     * @return int
     */
    public int saveType(Type type);

    /**
     * 获取指定分类
     * @param id
     * @return Type
     */
    public Type getType(Long id);

    /**
     * 根据名称获取分类
     * @param name
     * @return Type
     */
    public Type getTypeByName(String name);

    /**
     * 分页查询所有分类
     * @param pageNum
     * @return Page<Type>
     */
    public Page<Type> listType(Integer pageNum);

    /**
     * 查询所有分类
     * @return
     */
    public List<Type> listType();

    /**
     * 根据blog数量排序返回Type
     * @return
     */
    public List<Type> listTypeSortByBlogNum();

    /**
     * 删除指定分类
     * @param id
     * @return int
     */
    public int deleteType(Long id) throws NotFoundException;

    /**
     * 更新指定分类
     * @param id type
     * @return int
     */
    public int updateType(Long id,Type type) throws NotFoundException;
}
