package com.loiterer.listener.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loiterer.listener.user.entity.User;
import com.loiterer.listener.user.query.UserQuery;

import java.util.List;
import java.util.Map;

/**
 * @author XieZhiJie
 * @date 2020/10/24 21:30
 */
public interface UserService {

    /**
     * 查找所有user
     * @return 返回带有所有user的列表
     */
    List<User> findAll();

    /**
     * 获取User列表并分页
     * @param page  当前页
     * @param limit 每页多少数据
     * @return      返回分页信息的一个map集合
     */
    Map<String, Object> pageList(Long page, Long limit);

    /**
     * 获取User列表并分页
     * @param page      当前页
     * @param limit     每页多少数据
     * @param userQuery name模糊查询和age等值查询的数据
     * @return          返回分页信息的一个map集合
     */
    Map<String, Object> pageList(Long page, Long limit, UserQuery userQuery);

}
