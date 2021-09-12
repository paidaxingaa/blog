package com.liyue.blog.dao;

import com.liyue.blog.entity.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface TypeMapper {
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
    public Type getType(@Param("id") Long id);

    /**
     * 根据名称获取指定分类
     * @param name
     * @return Type
     */
    public Type getTypeByName(@Param("name")String name);

    /**
     * 查询所有分类
     * @return List<Type>
     */
    public List<Type> listType();

    /**
     * 删除指定分类
     * @param id
     */
    public int deleteType(@Param("id") Long id);

    /**
     * 更新指定分类
     * @param type
     */
    public int updateType(Type type);
}
