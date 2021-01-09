package com.tao.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tao.health.dao.SetmealDao;
import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.exception.MyException;
import com.tao.health.pojo.CheckItem;
import com.tao.health.pojo.Setmeal;
import com.tao.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/8 19:49
 */
//这里一定要注意一定要在括号里添加接口的字节码对象
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐信息
        setmealDao.add(setmeal);
        //获取添加套餐后的id
        Integer setmealId = setmeal.getId();

        //再添加套餐与检查组的关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {

                setmealDao.addSetmealCheckgroup(setmealId, checkgroupId);
            }
        }
        //添加事务控制
    }

    /**
     * 分页条件查询
     *
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {

        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //模糊查询 拼接%
        //判断是否有查询条件--这里使用的这个方法导致耽误了许久。写成了isEmpty
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            //有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //查询语句会被分页
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<Setmeal>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询选中的检查组id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);

    }

    /**
     * 修改套餐
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 更新套餐
        setmealDao.update(setmeal);
        // 删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        // 遍历添加 新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckgroup(setmeal.getId(), checkgroupId);
            }
        }
        // 事务控制
    }

    /**
     * 通过id 删除套餐
     * @param id
     */
    @Override
    public void deleteById(int id) {
        // 先判断 是否被订单使用了
        int count = setmealDao.findCountBySetmealId(id);
        // 使用了，要报错，接口方法 异常声明
        if(count > 0){
            throw new MyException("该套餐被订单使用了，不能删除");
        }
        // 没使用，则要先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    /**
     * 查询数据库中的所有图片
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }
}
