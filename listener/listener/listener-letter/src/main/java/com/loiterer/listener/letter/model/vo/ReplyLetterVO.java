package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 回信需要用到的vo类, 前端保存信息需要用到
 * @author XieZhiJie
 * @date 2020/11/10 12:04
 */
@Data
public class ReplyLetterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收信人id
     */
    private Integer recipientId;

    /**
     * 收信人笔名
     */
    private String recipientNickName;

    /**
     * 信件标题
     */
    private String title;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信封样式id
     */
    private Integer envelopeId;

}
