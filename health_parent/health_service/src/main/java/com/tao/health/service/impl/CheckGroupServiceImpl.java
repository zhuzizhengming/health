package com.tao.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tao.health.dao.CheckGroupDao;
import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.exception.MyException;
import com.tao.health.pojo.CheckGroup;
import com.tao.health.pojo.CheckItem;
import com.tao.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/7 14:49
 */

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组信息
     *
     * @param checkGroup   检查组信息
     * @param checkitemIds 选中的检查项id
     */
    @Override
    //添加事务控制
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先添加检查组的信息
        checkGroupDao.add(checkGroup);
        //- 获取检查组的id
        Integer checkgroupId = checkGroup.getId();
        //在添加检查项的信息
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                //添加检查组的检查项信息
                checkGroupDao.addCheckGroupCheckItem(checkgroupId, checkitemId);
            }
        }

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
        Page<CheckItem> page=checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }


    /**
     * 编辑检查组根据id回显数据
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //- 先更新检查组
        checkGroupDao.update(checkGroup);
        //- 先删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //- 遍历选中的检查项id的数组
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //- 添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
        //- 添加事务控制
    }

    /**
     * 通过检查组id查询绑定检查项id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
       return checkGroupDao.findCheckItemIdsByCheckGroupId(id);

    }

    /**
     * 根据id删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //查询该检查组是否被套餐使用
      int count= checkGroupDao.findCountByCheckGroupId(id);
        if (count !=0) {
        //若是有则不能删除，需要抛出自定义异常
            throw new MyException("该检查组已被套餐调用，不能删除");
        }
        //若没有使用则可以调用方法进行删除
        //先删除检查项与检查组的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //再删除该检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
