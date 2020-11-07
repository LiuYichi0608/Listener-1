package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.letter.model.entity.EnvelopeStyle;
import com.loiterer.listener.letter.service.EnvelopeStyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.loiterer.listener.common.util.ParameterVerificationUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 *  信的样式
 * </p>
 *
 * @author xzj
 * @since 2020-11-05
 */
@Slf4j
@RestController
@RequestMapping("/letter/envelope-style")
public class EnvelopeStyleController {

    private final EnvelopeStyleService envelopeStyleService;

    @Autowired
    public EnvelopeStyleController(EnvelopeStyleService envelopeStyleService) {
        this.envelopeStyleService = envelopeStyleService;
    }

    /**
     * 根据id查找信件和信封的样式
     * @param id 要查找的信件和信封的id
     * @return   返回信件和信封的url
     */
    @GetMapping("/get/envelope/style/{id}")
    public ResultEntity getEnvelopeStyleById(
            @PathVariable("id") String id
    ) {
        // 参数校验
        if (!ParameterVerificationUtil.isInt(id)) {
            return ResultEntity.fail().message("请输入整数!");
        }
        EnvelopeStyle envelopeStyle = envelopeStyleService.getById(id);
        return ResultEntity.success().data("envelopeStyle", envelopeStyle);
    }

}

