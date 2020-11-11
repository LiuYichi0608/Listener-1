package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于保存草稿的内容
 * @author XieZhiJie
 * @date 2020/11/11 20:59
 */
@Data
public class DraftBoxSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 草稿id, 更新使用
     */
    private Integer id;

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

}
