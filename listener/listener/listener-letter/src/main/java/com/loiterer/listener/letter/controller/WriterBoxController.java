package com.loiterer.listener.letter.controller;

import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.WriterBoxVO;
import com.loiterer.listener.letter.service.WriterBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            @RequestBody WriterBoxVO writerBoxSaveVO,
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

    /**
     * 获取该用户所有自己写的信件
     * @param request 从request中获取token并获取到用户的openid
     * @return        返回该用户自己写的所有信件
     */
    @GetMapping("/get/all/writer/letters")
    public ResultEntity getAllWriterLetters(
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.获取用户所有自己写的信件
        List<WriterBoxVO> writerBoxVOList = writerBoxService.getAllWriterLettersByOpenid(openid);

        // 3.获取失败则返回失败信息, 否则返回成功信息以及信件列表
        if (writerBoxVOList == null) {
            return ResultEntity.fail().message("获取信件失败");
        }

        return ResultEntity.success().data("writerBoxList", writerBoxVOList);
    }

}

