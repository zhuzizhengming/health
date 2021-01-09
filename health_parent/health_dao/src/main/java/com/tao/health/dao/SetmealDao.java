package com.tao.health.dao;

import com.github.pagehelper.Page;
import com.tao.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/8 20:00
 */
public interface SetmealDao {
    /**
     * 添加套餐信息
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐与检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetmealCheckgroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByCondition(String queryString);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据套餐id查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 修改套餐
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 删除套餐与检查组的旧关系
     * @param id
     */
    void deleteSetmealCheckGroup(Integer id);

    /**
     * 根据套餐id查询是否被订单使用
     * @param id
     * @return
     */
    int findCountBySetmealId(int id);

    /**
     * 删除套餐
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询数据库中的所有图片的url
     * @return
     */
    List<String> findImgs();

    /**
     * Setmeal查询所有
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 使用套餐id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
