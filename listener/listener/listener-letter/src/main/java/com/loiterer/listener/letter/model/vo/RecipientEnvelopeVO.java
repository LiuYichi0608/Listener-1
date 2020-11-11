package com.loiterer.listener.letter.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前后端进行收件箱数据交互时传输的数据
 * @author lyc
 * @date 2020/11/09 22:50
 */
@Data
public class RecipientEnvelopeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收件箱信件id
     */
    private Integer id;


    /**
     * 写信人id
     */
    private Integer writerId;

    /**
     * 写信人笔名
     */
    private String writerNickName;

    /**
     * 收信人id
     */
    private  Integer recipientId;

    /**
     * 收信人笔名
     */
    private String recipientNickName;

    /**
     * 信件标题
     */
    private String title;

    /**
     * 信封样式url
     */
    private String urlEnvelope;

    /**
     * 信件已读标志
     */
    private Integer isRead;

    /**
     * 写信保存的时间
     */
    private Date gmtCreate;
}
