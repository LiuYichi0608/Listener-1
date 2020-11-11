package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 一封信的主要数据
 * @author XieZhiJie
 * @date 2020/11/08 23:47
 */
@Data
public class WriterBoxContentVO implements Serializable {

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
     * 收信人id
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
     * 信件内容
     */
    private String content;

    /**
     * 信纸样式url
     */
    private String urlPaper;

    /**
     * 是否是回信, 不是回信为0, 否则为1
     */
    private Integer isReply;

    /**
     * 写信保存的时间
     */
    private Date gmtCreate;

}
