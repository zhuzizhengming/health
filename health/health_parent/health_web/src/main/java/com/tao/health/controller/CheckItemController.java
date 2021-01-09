package com.tao.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tao.health.constant.MessageConstant;
import com.tao.health.entity.PageResult;
import com.tao.health.entity.QueryPageBean;
import com.tao.health.entity.Result;
import com.tao.health.pojo.CheckItem;
import com.tao.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:诸子争鸣
 * @DATE:2021/1/5 19:34
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    /**
     * 订阅检查项服务
     */
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        //封装到rusult再返回
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
    }

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用服务进行分页查询
        //这个描述当前集合中只能存储CheckItem检查项
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //返回给页面，包装到Result对象。
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 编辑检查项根据id回显
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        //调用服务，根据id查询检查项
        CheckItem checkItem= checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);

    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //调用服务，执行编辑检查项
        checkItemService.update(checkItem);

        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);

    }

    /**
     * 根据id删除检查项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用服务，根据id删除检查项
        checkItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);

    }
}
