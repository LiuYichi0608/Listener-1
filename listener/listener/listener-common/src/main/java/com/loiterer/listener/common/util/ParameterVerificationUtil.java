package com.loiterer.listener.common.util;

/**
 * 参数校验的工具类
 * @author XieZhiJie
 * @date 2020/11/06 0:53
 */
public class ParameterVerificationUtil {

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
