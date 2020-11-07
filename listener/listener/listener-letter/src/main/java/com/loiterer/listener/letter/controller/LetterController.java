package com.loiterer.listener.letter.controller;

import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.LetterVO;
import com.loiterer.listener.letter.service.LetterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzj
 * @since 2020-11-06
 */
@Slf4j
@RestController
@RequestMapping("/letter/letter")
public class LetterController {

    private final LetterService letterService;

    /**
     * 用于从前端传入的用户的token中获取用户的信息
     */
    private final JwtUtil jwtUtil;

    @Autowired
    public LetterController(
            LetterService letterService,
            JwtUtil jwtUtil
    ) {
        this.letterService = letterService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用于在mysql数据库中添加一条信件信息
     * @param letterVO 前端传来的需要保存的信件信息
     * @param request  从请求头中获取token
     * @return         返回成功信息
     */
    @PostMapping("/add/letter")
    public ResultEntity addLetter(
            @RequestBody LetterVO letterVO,
            HttpServletRequest request
    ) {

        log.debug("信件的信息: {}", letterVO);

        // 从token中获取openid(不用考虑token不存在的情况, 不存在就被拦下了)
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        log.debug("用户的openid为: {}", openid);

        boolean flag = letterService.saveLetter(letterVO, openid);
        // 当保存信件失败后
        if (!flag) {
            return ResultEntity.fail().message("发送信件失败!");
        }
        // 当保存信件成功后
        return ResultEntity.success().message("发送信件成功!");
    }

}
