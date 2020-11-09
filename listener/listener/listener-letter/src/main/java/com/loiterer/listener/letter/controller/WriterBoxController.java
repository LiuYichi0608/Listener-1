package com.loiterer.listener.letter.controller;

import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.WriterBoxSaveVO;
import com.loiterer.listener.letter.service.WriterBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 *  写信相关的Controller
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@Slf4j
@RestController
@RequestMapping("/letter/writer-box")
public class WriterBoxController {

    /**
     * 写信相关操作的逻辑service类
     */
    private final WriterBoxService writerBoxService;

    /**
     * 使用jwt工具类从token中获取用户信息
     */
    private final JwtUtil jwtUtil;

    /**
     * 构造方法注入该Controller需要用到的类
     * @param writerBoxService 写信相关操作的逻辑service类
     * @param jwtUtil          使用jwt工具类从token中获取用户信息
     */
    @Autowired
    public WriterBoxController(
            WriterBoxService writerBoxService,
            JwtUtil jwtUtil
    ) {
        this.writerBoxService = writerBoxService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 保存信件信息
     * @param writerBoxSaveVO 要保存的信件信息
     * @param request         从request中获取token并获取到用户的openid
     * @return                返回保存信件是否成功的信息
     */
    @PostMapping("/add/letter")
    public ResultEntity addLetter(
            @RequestBody WriterBoxSaveVO writerBoxSaveVO,
            HttpServletRequest request
            ) {

        // 1.从request中获取到用户的token并使用jwt工具类获取到用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service类来保存该信件
        boolean flag = writerBoxService.writeLetter(writerBoxSaveVO, openid);

        // 3.判断是否保存该信件成功
        // 保存失败
        if (!flag) {
            return ResultEntity.fail().message("保存信件失败!");
        }

        // 保存成功
        return ResultEntity.success();
    }

}

