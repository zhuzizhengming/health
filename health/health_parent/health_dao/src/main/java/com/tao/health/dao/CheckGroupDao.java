package com.tao.health.dao;

import com.github.pagehelper.Page;
import com.tao.health.pojo.CheckGroup;
import com.tao.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/7 14:52
 */
public interface CheckGroupDao {
    /**
     * 添加检查组
     * @param checkGroup 检查组信息
     *
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组中的检查项
     * @param checkgroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkgroupId") Integer checkgroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 编辑检查组根据id回显数据
     * @param id
     */
    CheckGroup findById(int id);

    /**
     * 更新检查组信息
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除旧的关系
     * @param id
     */
    void deleteCheckGroupCheckItem(Integer id);

    /**
     * 通过检查组id查询绑定检查项id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    /**
     * 查询该检查组是否被套餐使用
     * @param id
     * @return
     */
    int findCountByCheckGroupId(int id);

    /**
     * 调用方法进行删除检查组
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

}
