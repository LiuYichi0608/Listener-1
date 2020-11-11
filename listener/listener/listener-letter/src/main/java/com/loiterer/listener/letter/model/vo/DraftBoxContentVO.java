package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端传来需要保存到后端的信息用此vo类保存起来
 * 后端需要传到前端的草稿信息用此vo类返回
 * @author XieZhiJie
 * @date 2020/11/10 14:42
 */
@Data
public class DraftBoxContentVO implements Serializable {

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
     * 收信人id
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
     * 草稿内容
     */
    private String content;

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
     * 草稿最近一次修改的时间
     */
    private Date gmtModified;

}
