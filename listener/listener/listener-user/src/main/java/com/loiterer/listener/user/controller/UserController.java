package com.loiterer.listener.user.controller;

import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.user.model.dto.LoginDTO;
import com.loiterer.listener.user.model.dto.UserInfoDTO;
import com.loiterer.listener.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户的 controller 层
 *
 * @author cmt
 * @date 2020/10/21
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param code 登录凭证
     * @return 如果登录成功，则返回 openid 和 token；否则返回错误信息
     */
    @PostMapping("/login")
    public ResultEntity login(@RequestParam("code") String code) {
        if (StringUtils.isEmpty(code)) {
            return ResultEntity.fail().message("登录凭证code为空");
        }

        LoginDTO loginDTO = userService.login(code);
        if (loginDTO == null) {
            return ResultEntity.fail().message("请求微信授权获取openid失败");
        }
        return ResultEntity.success().data("loginDTO", loginDTO);
    }

    /**
     * 插入用户信息; Header 中需要携带token
     *
     * @param request     http请求对象
     * @param userInfoDTO 用户信息
     */
    @PostMapping("/insertUserInfo")
    public ResultEntity insertUserInfo(HttpServletRequest request, UserInfoDTO userInfoDTO) {
        log.info(userInfoDTO.toString());
        String token = request.getHeader("token");
        UserInfoDTO userInfo = userService.insertUserInfo(userInfoDTO, token);
        return ResultEntity.success().data("userInfo", userInfo);
    }

    /**
     * 修改用户昵称; Header 中需要携带token
     *
     * @param request  http请求对象
     * @param nickName 用户昵称
     */
    @GetMapping("/updateNickname")
    public ResultEntity updateNickName(HttpServletRequest request, @RequestParam("nickName") String nickName) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(nickName) || nickName.length() > 10) {
            return ResultEntity.fail().message("昵称不能为空且昵称长度需小于10");
        }
        userService.updateNickName(nickName, token);
        return ResultEntity.success();
    }

    /**
     * 查询用户信息; Header 中需要携带token
     *
     * @param request http请求对象
     * @return 返回用户信息
     */
    @GetMapping("/selectUserInfo")
    public ResultEntity selectUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoDTO userInfoDTO = userService.selectUserInfo(token);
        return ResultEntity.success().data("userInfo", userInfoDTO);
    }
}
