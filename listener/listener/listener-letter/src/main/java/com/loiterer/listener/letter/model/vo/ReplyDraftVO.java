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
     * 信封样式url
     */
    private String urlEnvelope;

    /**
     * 信纸样式url
     */
    private String urlPaper;

    /**
     * 是否是回信的草稿, 不是回信的草稿为0, 否则为1
     */
    private Integer isReply;

    /**
     * 草稿保存的时间
     */
    private Date gmtCreate;

    /**
     * 草稿最进一次修改的时间
     */
    private Date gmtModified;

}
