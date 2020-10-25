package com.loiterer.listener.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前端的统一数据格式
 * @author XieZhiJie
 * @date 2020/10/24 21:34
 */
@Data
public class ResultEntity {

    /**
     * 返回给前端的状态码
     */
    private Integer status;

    /**
     * 返回给前端的信息
     */
    private String message;

    /**
     * 返回个前端的具体数据
     */
    private Map<String, Object> data = new HashMap<>(16);

    /**
     * 构造方法私有化
     */
    private ResultEntity() {}

    /**
     * 当方法调用成功的时候设置值并返回一个ResultEntity对象
     * @return 返回一个ResultEntity对象方便链式设值
     */
    public static ResultEntity success() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setStatus(ResultCode.SUCCESS);
        resultEntity.setMessage("success");
        return resultEntity;
    }

    /**
     * 当方法调用失败的时候设值值并返回一个ResultEntity对象
     * @return 返回一个ResultEntity对象方便链式设值
     */
    public static ResultEntity fail() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setStatus(ResultCode.FAIL);
        resultEntity.setMessage("fail");
        return resultEntity;
    }

    /**
     * 设置状态码
     * @param status 要设置的状态码的值
     * @return       返回当前对象方便链式设值
     */
    public ResultEntity status(Integer status) {
        this.setStatus(status);
        return this;
    }

    /**
     * 设置信息
     * @param message 要设置的信息的值
     * @return        返回当前对象方便链式设值
     */
    public ResultEntity message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 将数据放入data中
     * @param key   数据的key
     * @param value 数据的值
     * @return      返回当前对象方便链式设值
     */
    public ResultEntity data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 将一个map格式的数据设置成为data, map中可存放多个键值对
     * @param map 要设置成为data的map
     * @return    返回当前对象方便链式设值
     */
    public ResultEntity data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
