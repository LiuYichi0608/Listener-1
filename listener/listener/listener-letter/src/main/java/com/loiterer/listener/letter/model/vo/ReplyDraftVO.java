package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作为回信的草稿的VO类
 * @author XieZhiJie
 * @date 2020/11/10 20:21
 */
@Data
public class ReplyDraftVO implements Serializable {

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
     * 草稿标题
     */
    private String title;

    /**
     * 草稿内容
     */
    private String content;

    /**
     * 草稿样式id
     */
    private Integer envelopeId;

    /**
     * 要回复的那封信的id
     */
    private Integer replyId;

}
