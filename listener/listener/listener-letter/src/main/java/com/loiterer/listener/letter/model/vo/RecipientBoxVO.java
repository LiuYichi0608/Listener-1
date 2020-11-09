package com.loiterer.listener.letter.model.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 前后端进行收件箱数据交互时传输的数据
 * @author lyc
 * @date 2020/11/09 22:50
 */
@Data
public class RecipientBoxVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收件箱信件id
     */
    private Integer id;

    /**
     * 写信人笔名
     */
    private String writerNickname;

    /**
     * 收信人笔名
     */
    private String recipientNickname;

    /**
     * 信件标题
     */
    private String title;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信封样式url
     */
    private String urlEnvelope;

    /**
     * 信纸样式url
     */
    private String urlPaper;

    /**
     * 信件已读标志
     */
    private Integer isRead;
}
