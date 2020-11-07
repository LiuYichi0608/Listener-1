package com.loiterer.listener.user.service;

import com.loiterer.listener.user.model.dto.LoginDTO;
import com.loiterer.listener.user.model.dto.UserInfoDTO;

/**
 * 用户的 service 层
 *
 * @author cmt
 * @date 2020/10/21
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param code 用户登录凭证
     * @return     返回 openid 和 token
     */
    LoginDTO login(String code);

    /**
     * 插入用户信息
     *
     * @param userInfoDTO 用户信息
     * @param token       token
     * @return            返回用户信息
     */
    UserInfoDTO insertUserInfo(UserInfoDTO userInfoDTO, String token);

    /**
     * 修改用户昵称
     *
     * @param nickName 用户昵称
     * @param token    token
     */
    void updateNickName(String nickName, String token);

    /**
     * 查询用户信息
     *
     * @param token token
     * @return      返回用户信息
     */
    UserInfoDTO selectUserInfo(String token);
}
