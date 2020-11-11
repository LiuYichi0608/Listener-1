package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当返回草稿列表的时候, 要返回的每一封草稿的信息
 * @author XieZhiJie
 * @date 2020/11/11 21:10
 */
@Data
public class DraftBoxEnvelopeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 草稿id
     */
    private Integer id;

    /**
     * 写信人笔名
     */
    private String writerNickName;

    /**
     * 收件人id
     */
    private Integer recipientId;

    /**
     * 收信人昵称
     */
    private String recipientNickName;

    /**
     * 草稿标题
     */
    private String title;

    /**
     * 草稿样式url
     */
    private String urlEnvelope;

    /**
     * 是否是回信, 不是回信为0, 否则为1
     */
    private Integer isReply;

    /**
     * 写信保存的时间
     */
    private Date gmtCreate;

    /**
     * 最近一次保存草稿的时间
     */
    private Date gmtModified;

}
