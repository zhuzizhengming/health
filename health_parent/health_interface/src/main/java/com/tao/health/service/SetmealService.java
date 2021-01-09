package com.tao.health.service;

import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.pojo.Setmeal;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/8 19:49
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 分页条件查询
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

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
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 通过id删除套餐
     * @param id
     */
    void deleteById(int id);

    /**
     * 查出数据库中的所有图片
     * @return
     */
    List<String> findImgs();

    /**
     * 查询所有
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 根据套餐id查询套餐详情
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
