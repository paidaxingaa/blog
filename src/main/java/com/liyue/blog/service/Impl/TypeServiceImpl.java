package com.liyue.blog.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liyue.blog.dao.TypeMapper;
import com.liyue.blog.entity.Type;
import com.liyue.blog.service.TypeService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liyue
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveType(Type type){
        type.setCreateTime(new Date());
        int result = typeMapper.saveType(type);
        return result;
    }

    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public Page<Type> listType(Integer pageNum) {
        Page<Type> page = PageHelper.startPage(pageNum, 6).doSelectPage(() -> typeMapper.listType());
        return page;
    }

    @Override
    public List<Type> listType() {
        return typeMapper.listType();
    }

    @Override
    public List<Type> listTypeSortByBlogNum() {
        PageHelper.startPage(1,6,"id asc");
        List<Type> types = typeMapper.listType();
        return types;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteType(Long id) throws NotFoundException {
        Type t = typeMapper.getType(id);
        if ( t == null){
            throw new NotFoundException("不存在该分类");
        }
        return typeMapper.deleteType(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateType(Long id,Type type) throws NotFoundException{
        Type t = typeMapper.getType(id);
        if ( t == null){
            throw new NotFoundException("不存在该分类");
        }
        type.setUpdateTime(new Date());
        return typeMapper.updateType(type);
    }
}
