package com.loiterer.listener.common.result;

/**
 * 自定义状态码
 * @author XieZhiJie
 * @date 2020/10/24 21:36
 */
public interface ResultCode {

    /**
     * 自定义成功状态码
     */
    Integer SUCCESS = 20000;

    /**
     * 自定义失败错误的状态码
     */
    Integer FAIL = 20001;

    /**
     * 自定义无法处理该请求的状态码
     */
    Integer NOT_FOUND = 404;

}
