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
     * @param token  token
     */
    void insertOpenidAndToken(@Param("openid") String openid, @Param("token") String token);

    /**
     * 用户每一次登录，通过 openid 更新 token
     *
     * @param openid 微信用户的唯一标识
     * @param token  token
     * @return
     */
    void updateToken(@Param("openid") String openid, @Param("token") String token);

    /**
     * 插入用户信息
     *
     * @param userInfoDTO 用户信息
     * @param token       token
     */
    void insertUserInfo(@Param("userInfo") UserInfoDTO userInfoDTO, @Param("token") String token);

    /**
     * 修改用户昵称
     *
     * @param nickName 用户昵称
     * @param token    token
     */
    void updateNickName(@Param("nickName") String nickName, @Param("token") String token);

    /**
     * 查询用户信息
     *
     * @param token token
     * @return 返回用户信息
     */
    UserInfoDTO selectUserInfo(@Param("token") String token);
}
