package com.loiterer.listener.common.result;

import lombok.Getter;

/**
 * 自定义状态码
 * @author XieZhiJie
 * @date 2020/10/26 21:52
 */
public enum ResultCodeEnum {

    /**
     * 自定义成功状态码
     */
    SUCCESS(20000),

    /**
     * 自定义失败状态码
     */
    FAIL(20001),

    /**
     * 自定义404状态码
     */
    NOT_FOUND(404);

    @Getter
    private final Integer code;

    ResultCodeEnum(Integer code) {
        this.code = code;
    }

}
