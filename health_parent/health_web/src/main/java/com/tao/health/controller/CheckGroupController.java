package com.tao.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tao.health.constant.MessageConstant;
import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.entity.Result;
import com.tao.health.pojo.CheckGroup;
import com.tao.health.pojo.CheckItem;
import com.tao.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/7 14:41
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组信息
     *
     * @param checkGroup   检查组信息
     * @param checkitemIds 选中的检查项id数组
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.add(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用服务进行分页查询
        //这个描述当前集合中只能存储CheckItem检查项
        PageResult<CheckItem> pageResult = checkGroupService.findPage(queryPageBean);
        //返回给页面，包装到Result对象。
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 编辑检查组根据id回显数据
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 通过检查组id查询绑定检查项id
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id) {
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);

    }

    /**
     * 修改检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.update(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);

    }

    /**
     * 根据id删除检查组
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);

    }

    /**
     * 查询所有的检查组
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup>list=checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
