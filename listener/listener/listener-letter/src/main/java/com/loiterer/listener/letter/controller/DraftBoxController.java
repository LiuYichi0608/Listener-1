package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;
import com.loiterer.listener.letter.service.DraftBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/letter/draft-box")
public class DraftBoxController {

    /**
     * 草稿相关操作的逻辑service类
     */
    private final DraftBoxService draftBoxService;

    /**
     * 使用jwt工具类从token中获取用户信息
     */
    private final JwtUtil jwtUtil;

    /**
     * 构造方法注入该Controller需要用到的类
     * @param draftBoxService 写信相关操作的逻辑service类
     * @param jwtUtil         使用jwt工具类从token中获取用户信息
     */
    @Autowired
    public DraftBoxController(
            DraftBoxService draftBoxService,
            JwtUtil jwtUtil
    ) {
        this.draftBoxService = draftBoxService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 插入草稿信息
     * @param draftBoxVO 要保存的信件信息
     * @param request    从request中获取token并获取到用户的openid
     * @return           返回保存信件是否成功的信息
     */
    @PostMapping("/add/draft")
    public ResultEntity addDraft(
            DraftBoxVO draftBoxVO,
            HttpServletRequest request
    ) {

        // 1.从request中获取到用户的token并使用jwt工具类获取到用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service类来保存草稿
        boolean flag = draftBoxService.addDraft(draftBoxVO, openid);

        // 3.判断是否保存草稿成功
        if (!flag) {
            return ResultEntity.fail().message("保存草稿失败!");
        }
        return ResultEntity.success().message("保存草稿成功!");
    }

}

