package com.loiterer.listener.letter.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loiterer.listener.common.exception.ListenerException;
import com.loiterer.listener.common.result.ResultCodeEnum;
import com.loiterer.listener.user.mapper.UserMapper;
import com.loiterer.listener.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 封装了获取用户信息方法的工具类
 * @author XieZhiJie
 * @date 2020/11/10 15:12
 */
@Slf4j
@Component
public class UserUtil {

    /**
     * 需要用到userMapper来获取用户信息
     */
    private final UserMapper userMapper;

    @Autowired
    public UserUtil(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据columns信息获取user的信息
     * @param openid  用户的openid
     * @param columns 要获取的字段的信息
     * @return        返回用户信息
     */
    public User getUserInfo(String openid, String... columns) {
        // 1.封装条件
        QueryWrapper<User> draftQueryWrapper = new QueryWrapper<>();
        draftQueryWrapper.select(columns);
        log.debug("用户的openid为: {}", openid);
        draftQueryWrapper.eq("openid", openid);

        // 2.根据条件查找信息
        User writerUser = userMapper.selectOne(draftQueryWrapper);

        // 3.判断用户信息是否存在, 不存在抛出异常, 否则返回用户信息
        if (writerUser == null) {
            throw new ListenerException(ResultCodeEnum.FAIL.getCode(), "获取用户信息失败!");
        }
        return writerUser;
    }


    /**
     * 查询用户数量
     * @param queryWrapper 查询条件
     * @return             返回用户数量
     */
    public int selectCount(QueryWrapper<User> queryWrapper) {
        return userMapper.selectCount(queryWrapper);
    }

    /**
     * 根据一组id查找用户信息
     * @param collection 一组id
     * @return           返回一组用户信息
     */
    public List<User> selectBatchIds(Collection<Integer> collection) {
        return userMapper.selectBatchIds(collection);
    }

}
