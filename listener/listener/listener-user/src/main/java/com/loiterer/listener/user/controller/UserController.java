package com.loiterer.listener.user.controller;

import com.loiterer.listener.common.exception.ListenerException;
import com.loiterer.listener.common.result.ResultCodeEnum;
import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.user.query.UserQuery;
import com.loiterer.listener.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author XieZhiJie
 * @date 2020/10/24 21:33
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UserController(UserService userService, RedisTemplate<String, String> redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 抛出错误的一个测试接口
     * @return 返回一个错误
     */
    @RequestMapping("/error")
    public ResultEntity error() {
        throw new ListenerException(ResultCodeEnum.FAIL.getCode(), "自定义异常!");
    }

    /**
     * 分页测试, 包括姓名模糊查询和年龄等值查询
     * @param page      当前页
     * @param limit     一页多少数据
     * @param userQuery 查询信息
     * @return          返回查询到的信息
     */
    @PostMapping("/page/{page}/{limit}")
    public ResultEntity page(
            @PathVariable("page") Long page,
            @PathVariable("limit") Long limit,
            @RequestBody(required = false) UserQuery userQuery
            ) {
        // 还需要做参数校验, 我没做
        Map<String, Object> data = userService.pageList(page, limit, userQuery);
        return ResultEntity.success().data(data);
    }

    /**
     * 分页测试
     * @param page  当前页
     * @param limit 每页多少数据
     * @return      返回前端数据
     */
    @GetMapping("/page/{page}/{limit}")
    public ResultEntity page(
            @PathVariable("page") Long page,
            @PathVariable("limit") Long limit
    ) {
        Map<String, Object> data = userService.pageList(page, limit);

        return ResultEntity.success().data(data);
    }

    /**
     * 用于测试能够获得mysql和redis数据库的数据并返回
     * Get提交方式
     * @return 返回数据
     */
    @RequestMapping("/findAll")
    public ResultEntity findAll() {
        return ResultEntity.success()
                .data("mysqlData", userService.findAll())
                .data("redisData", redisTemplate.opsForValue().get("a"));
    }

}
