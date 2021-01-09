package com.tao.health.service;

import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.pojo.CheckGroup;
import com.tao.health.pojo.CheckItem;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/7 14:48
 */
public interface CheckGroupService {
    /**
     * 添加检查组信息
     * @param checkGroup 检查组信息
     * @param checkitemIds  选中的检查项id
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 编辑检查组根据id回显数据
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds);


    /**
     * 通过检查组id查询绑定检查项id
     *
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 根据id删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

}
