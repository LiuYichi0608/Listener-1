package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;

import com.loiterer.listener.letter.model.vo.RecipientContentVO;
import com.loiterer.listener.letter.model.vo.RecipientEnvelopeVO;
import com.loiterer.listener.letter.service.RecipientBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyc
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/letter/recipientBox")
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

    /**
     * 获取该用户所有自己收信的信件
     * @param request 从request中获取openid
     * @return 返回该用户收到的的所有未删除信件
     */
    @GetMapping("/getLetters")
    public ResultEntity getAllRecipientLetters(HttpServletRequest request) {
        // 1.使用jwtUtil从请求头获取openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.获取用户所有自己收到的未删除的信件
        List<RecipientEnvelopeVO> recipientEnvelopeVOList = recipientBoxService.getAllRecipientLettersByOpenid(openid);

        // 3.判断获取信件结果
        if(recipientEnvelopeVOList == null) {
            // 3.1 获取信息失败, 返回失败信息
            return ResultEntity.fail().message("获取信息失败");
        } else {
            // 3.2 获取信件成功, 返回信件信息
            return ResultEntity.success().data("recipientEnvelopeVOList", recipientEnvelopeVOList);
        }
    }
    /**
     * 获取该用户最新10条自己收信的信件
     * @param request 从request中获取openid
     * @return 返回该用户收到的最新10条信件
     */
    @GetMapping("/getPageLetters")
    public ResultEntity getPageRecipientLetters(HttpServletRequest request) {
        // 1.使用jwtUtil从请求头获取openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.获取用户最新收到的十条信件
        List<RecipientEnvelopeVO> recipientEnvelopeVOList = recipientBoxService.getPageRecipientLettersByOpenid(openid);

        // 3.判断获取信件结果
        if(recipientEnvelopeVOList == null) {
            // 3.1 获取信息失败, 返回失败信息
            return ResultEntity.fail().message("获取信息失败");
        } else {
            // 3.2 获取信件成功, 返回信件信息
            return ResultEntity.success().data("recipientEnvelopeVOList", recipientEnvelopeVOList);
        }
    }

    /**
     * 根据信件id去删除指定信件
     * @param id 信件id
     * @param request 从request中获取openid
     * @return 返回用户是否成功删除信件信息
     */
    @DeleteMapping("/deleteLetter/{id}")
    public ResultEntity deleteRecipientLetter(@PathVariable("id") Integer id, HttpServletRequest request) {
        // 1.使用jwtUtil从请求头获取openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.获取用户所有自己收到的未删除的信件
        boolean flag = recipientBoxService.deleteRecipientLetter(id,openid);

        if(!flag) {
            // 3.1 删除信件失败
            return ResultEntity.fail().message("删除信件失败");
        } else {
            // 3.2 删除信件成功
            return ResultEntity.success().message("删除信件成功");
        }
    }


    /**
     * 根据信件id获取指定信件内容
     * @param id 信件id
     * @param request 从request中获取openid
     * @return 返回信件内容信息
     */
    @GetMapping("/getRecipientLetterContent/{id}")
    public ResultEntity getRecipientLetterContentById(@PathVariable("id") Integer id, HttpServletRequest request) {
        // 1.使用jwtUtil从请求头获取openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.根据用户openid和信件id获取当前信件内容信息
        RecipientContentVO recipientContentVO = recipientBoxService.getRecipientLetterById(id,openid);

        // 3.判断获取信件结果
        if(recipientContentVO == null) {
            // 3.1 获取信息失败, 返回失败信息
            return ResultEntity.fail().message("获取信息失败");
        } else {
            // 3.2 获取信件成功, 返回信件信息
            return ResultEntity.success().data("recipientContentVO", recipientContentVO);
        }
    }

}

