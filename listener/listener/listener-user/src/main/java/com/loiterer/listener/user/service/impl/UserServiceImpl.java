package com.loiterer.listener.user.service.impl;

import com.google.gson.Gson;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.common.util.RedisUtil;
import com.loiterer.listener.user.mapper.UserMapper;
import com.loiterer.listener.user.model.dto.LoginDTO;
import com.loiterer.listener.user.model.dto.UserInfoDTO;
import com.loiterer.listener.user.model.entity.WeChatSession;
import com.loiterer.listener.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 用户的 service 实现层
 *
 * @author cmt
 * @date 2020/10/21
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${loiterer.listener.app-id}")
    private String appId;
    @Value("${loiterer.listener.app-secret}")
    private String appSecret;

    @Override
    public LoginDTO login(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret
                + "&js_code=" + code + "&grant_type=authorization_code";

        // 使用 RestTemplate 调用第三方接口
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            WeChatSession weChatSession = gson.fromJson(responseEntity.getBody(), WeChatSession.class);

            log.info("errorCode: " + weChatSession.getErrcode());
            String openid = weChatSession.getOpenid();
            // 请求微信授权成功
            if (StringUtils.isEmpty(weChatSession.getErrcode())) {
                // 生成 token
                String token = jwtUtil.getToken(openid);
                if (StringUtils.isEmpty(userMapper.isOpenidExists(openid))) {
                    // openid 不存在，为第一次登录：插入 openid
                    userMapper.insertOpenid(openid);
                    // 以 openid 为 key，将 session_key 存入 redis
                    redisUtil.set(RedisUtil.USER_KEY_PREFIX + openid, weChatSession.getSession_key());
                }
                return new LoginDTO(openid, token);
            }
        }
        return null;
    }

    /**
     * 插入用户信息
     *
     * @param userInfoDTO 用户信息
     */
    @Override
    public UserInfoDTO insertUserInfo(UserInfoDTO userInfoDTO, String token) {
        userMapper.insertUserInfo(userInfoDTO, jwtUtil.getOpenid(token));
        System.out.println("service userInfo: " + userInfoDTO.toString());
        return userInfoDTO;
    }

    /**
     * 修改用户昵称
     *
     * @param nickName 用户昵称
     */
    @Override
    public void updateNickName(String nickName, String token) {
        userMapper.updateNickName(nickName, jwtUtil.getOpenid(token));
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @Override
    public UserInfoDTO selectUserInfo(String token) {
        return userMapper.selectUserInfo(jwtUtil.getOpenid(token));
    }
}
