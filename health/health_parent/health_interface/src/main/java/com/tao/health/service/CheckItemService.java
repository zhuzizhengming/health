package com.tao.health.service;

import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.exception.MyException;
import com.tao.health.pojo.CheckItem;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/5 19:51
 */
public interface CheckItemService {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 编辑检查项根据id回显
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 编辑检查项
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(int id) throws MyException;
}
