package com.tao.health.dao;

import com.github.pagehelper.Page;
import com.tao.health.pojo.CheckItem;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/5 20:01
 */
public interface CheckItemDao {
    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 添加查询项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

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
     * 统计检查项被使用的次数
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);

    /**
     * 根据id删除检查项
     * @param id
     */
    void deleteById(int id);
}
