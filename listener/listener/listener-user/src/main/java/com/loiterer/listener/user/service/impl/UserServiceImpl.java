package com.loiterer.listener.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loiterer.listener.user.entity.User;
import com.loiterer.listener.user.mapper.UserMapper;
import com.loiterer.listener.user.query.UserQuery;
import com.loiterer.listener.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XieZhiJie
 * @date 2020/10/24 21:30
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit) {
        Page<User> userPage = new Page<>(page, limit);
        // 第二个参数传入null代表没有查询条件
        userMapper.selectPage(userPage, null);
        return pageToMap(userPage);
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit, UserQuery userQuery) {
        Page<User> userPage = new Page<>(page, limit);

        // 没有查询信息的时候
        if (userQuery == null) {
            userMapper.selectPage(userPage, null);
            return pageToMap(userPage);
        }

        // 查询信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        // 查询信息
        String name = userQuery.getName();
        Integer age = userQuery.getAge();

        // 当name有查询信息的时候
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", userQuery.getName());
        }

        // 当age有查询信息的时候
        if (userQuery.getAge() != null) {
            wrapper.eq("age", age);
        }

        userMapper.selectPage(userPage, wrapper);

        return pageToMap(userPage);
    }

    /**
     * 当前类的将page的信息转化为map的共同代码块
     * @param userPage 查询出的page信息
     * @return         返回一个map集合
     */
    private Map<String, Object> pageToMap(Page<User> userPage) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("pageList", userPage.getRecords());
        data.put("total", userPage.getTotal());
        data.put("size", userPage.getSize());
        data.put("current", userPage.getCurrent());
        return data;
    }

}
