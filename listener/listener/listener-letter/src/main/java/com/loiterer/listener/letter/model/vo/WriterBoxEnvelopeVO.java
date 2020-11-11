package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当返回信件列表的时候, 要返回的每一封信件的信息
 * @author XieZhiJie
 * @date 2020/11/11 20:38
 */
@Data
public class WriterBoxEnvelopeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 信件id
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
     * 信件标题
     */
    private String title;

    /**
     * 信纸样式url
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

}
