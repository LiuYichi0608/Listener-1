package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端发送保存信件信息时传送的数据
 * 后端返回前端时需要携带的信息
 * @author XieZhiJie
 * @date 2020/11/08 23:47
 */
@Data
public class WriterBoxVO implements Serializable {

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

    /**
     * 信封样式url
     */
    private String urlEnvelope;

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
