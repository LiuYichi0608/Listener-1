package com.loiterer.listener.letter.controller;

import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.ReplyLetterVO;
import com.loiterer.listener.letter.model.vo.WriterBoxContentVO;
import com.loiterer.listener.letter.model.vo.WriterBoxEnvelopeVO;
import com.loiterer.listener.letter.model.vo.WriterBoxSaveVO;
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
        return ResultEntity.success().message("保存信件成功!");
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
        List<WriterBoxEnvelopeVO> writerBoxVOList = writerBoxService.getAllWriterLettersByOpenid(openid);

        // 3.获取失败则返回失败信息, 否则返回成功信息以及信件列表
        if (writerBoxVOList == null) {
            return ResultEntity.fail().message("获取信件失败");
        }

        return ResultEntity.success().data("writerBoxList", writerBoxVOList);
    }

    /**
     * 根据信件id和信件信息删除信件
     * @param id      信件id
     * @param request 从request中获取token并获取到用户的openid
     * @return        返回是否删除成功的信息
     */
    @DeleteMapping("/delete/letter/{id}")
    public ResultEntity deleteLetter(
            @PathVariable("id") Integer id,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.根据信件id和用户openid删除属于这个用户的信件
        boolean flag = writerBoxService.deleteLetterById(id, openid);

        // 3.判断删除是否成功
        if (!flag) {
            return ResultEntity.fail().message("删除失败!");
        }
        return ResultEntity.success().message("删除成功!");
    }

    /**
     * 保存回信信息
     * @param replyLetterVO 要保存的回信信息
     * @param request       从request中获取token并获取到用户的openid
     * @return              返回是否保存成功
     */
    @PostMapping("/add/reply/letter")
    public ResultEntity addReplyLetter(
            @RequestBody ReplyLetterVO replyLetterVO,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service类来保存该信件
        boolean flag = writerBoxService.saveReplyLetter(replyLetterVO, openid);

        // 3.判断是否保存该信件成功
        if (!flag) {
            return ResultEntity.fail().message("保存回信失败!");
        }
        return ResultEntity.success().message("保存回信成功!");
    }

    /**
     * 根据用户信息和信件id获取一封信的内容
     * @param id      信件id
     * @param request 从request中获取token并获取到用户的openid
     * @return        返回一封信的具体内容
     */
    @GetMapping("/get/letter/{id}")
    public ResultEntity getLetterByLetterId(
            @PathVariable("id") Integer id,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service获取一封信的内容
        WriterBoxContentVO writerBoxContentVO = writerBoxService.getLetterByLetterId(id, openid);

        // 3.获取失败返回失败信息, 否则返回信件内容与成功信息
        if (writerBoxContentVO == null) {
            return ResultEntity.fail().message("获取信件内容失败!");
        }

        return ResultEntity.success().data("writerBoxContent", writerBoxContentVO);
    }

}

