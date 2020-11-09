package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;

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
     * 是否是回信, 不是回信为0, 否则为1
     */
    private Integer isReply;

}
