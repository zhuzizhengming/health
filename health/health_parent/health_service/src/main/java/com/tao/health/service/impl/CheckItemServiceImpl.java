package com.tao.health.service.impl;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tao.health.dao.CheckItemDao;
import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.exception.MyException;
import com.tao.health.pojo.CheckItem;
import com.tao.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/5 19:56
 */

/**
 * 检查项服务,发布服务，interfaceClass指定服务的接口类
 */
@Service(interfaceClass =CheckItemService.class )
public class CheckItemServiceImpl implements CheckItemService {


    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 添加查询项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //第二种，Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //模糊查询 拼接%
        //判断是否有查询条件--这里使用的这个方法导致耽误了许久。写成了isEmpty
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            //有查询条件，拼接%
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //查询语句会被分页
        Page<CheckItem>page=checkItemDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 编辑检查项根据id回显
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {

        return checkItemDao.findById(id);
    }

    /**
     * 编辑检查项
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {
        //调用dao查询 统计这个检查项id被使用的个数
        int count=checkItemDao.findCountByCheckItemId(id);
        if (count>0) {
        //个数>0 说明被检查组使用了，不能删除，抛出自定义异常。【注意】接口方法要声明抛出异常类型
        throw new MyException("该检查项已被使用，不能删除！！");

        }
        //个数=0，则调用dao删除
        checkItemDao.deleteById(id);
    }
}
