package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.letter.entity.WriterBox;
import com.loiterer.listener.letter.service.WriterBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 *  写(发送)信件
 * </p>
 *
 * @author xzj
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/letter/writer-box")
public class WriterBoxController {

    private final WriterBoxService writerBoxService;

    @Autowired
    public WriterBoxController(WriterBoxService writerBoxService) {
        this.writerBoxService = writerBoxService;
    }

    /**
     * 接收用户刚写好的信件信息保存并发送给其它用户并记录收件人的id
     * @param writerBox 信件信息及其他用户信息
     * @return          返回成功状态
     */
    @PostMapping("/insert/letter")
    public ResultEntity insertLetter(
            @RequestBody WriterBox writerBox
            ) {
        writerBoxService.save(writerBox);
        return ResultEntity.success();
    }

}

