package com.loiterer.listener.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loiterer.listener.user.model.dto.UserInfoDTO;
import com.loiterer.listener.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 新用户注册时，插入 openid 和 token
     *
     * @param openid 微信用户的唯一标识
     */
    void insertOpenid(@Param("openid") String openid);

    /**
     * 查询是否存在 openid
     *
     * @param openid 微信用户的唯一标识
     * @return
     */
    String isOpenidExists(@Param("openid") String openid);

    /**
     * 插入用户信息
     *
     * @param userInfoDTO 用户信息
     * @param openid      微信唯一凭证
     */
    void insertUserInfo(@Param("userInfo") UserInfoDTO userInfoDTO, @Param("openid") String openid);

    /**
     * 修改用户昵称
     *
     * @param nickName 用户昵称
     * @param openid   微信唯一凭证
     */
    void updateNickName(@Param("nickName") String nickName, @Param("openid") String openid);

    /**
     * 查询用户信息
     *
     * @param openid 微信唯一凭证
     * @return       返回用户信息
     */
    UserInfoDTO selectUserInfo(@Param("openid") String openid);
}
