package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.util.JwtUtil;

import com.loiterer.listener.letter.service.RecipientBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/letter/recipient-box")
public class RecipientBoxController {

    /**
     * 写信相关操作的逻辑service类
     */
    private final RecipientBoxService recipientBoxService;

    /**
     * 使用jwt工具类从token中获取用户信息
     */
    private final JwtUtil jwtUtil;

    /**
     * 构造方法, 到时实现自动注入
     * @param recipientBoxService 收信箱操作相关的service类
     * @param jwtUtil 使用jwt工具类从token中获取用户信息
     */
    @Autowired
    public RecipientBoxController(RecipientBoxService recipientBoxService, JwtUtil jwtUtil) {
        this.recipientBoxService = recipientBoxService;
        this.jwtUtil = jwtUtil;
    }
}

