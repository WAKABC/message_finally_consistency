package com.wak.bank2.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author wuankang
 * @date 2023/11/05
 * @description TODO json工具类
 * @version 1.0.0
 */
@Slf4j
public class JsonUtil {

    /**
     * 获取指定key的json字符串
     * @param srcJsonStr 源json
     * @param expectedKey 目标json的key
     * @return {@link String}
     */
    public static String getSpecifiedJsonStr(String srcJsonStr, String expectedKey) {
        // Parse the JSON string into a JSON object
        JSONObject jsonObject = JSON.parseObject(srcJsonStr);
        // Get the value of the specified key
        String expectedValue = jsonObject.getString(expectedKey);
        // Return the value if it is not empty, otherwise return an empty string
        return StringUtils.hasText(expectedValue) ? expectedValue : "";
    }

    /**
     * Get the value of the specified key
     * @param srcJsonStr  源json字符串
     * @param expectedKey 目标json的key
     * @param t 需要转换的Class
     * @return {@link T}
     */
    public static <T> T getSpecifiedObj(String srcJsonStr, String expectedKey, Class<T> t) {
        if (!StringUtils.hasText(srcJsonStr) || !StringUtils.hasText(expectedKey) || ObjectUtils.isEmpty(t)) {
            throw new IllegalArgumentException("params has empty value, please check it");
        }
        // Parse the JSON string into a JSON object
        JSONObject jsonObject = JSON.parseObject(srcJsonStr);
        // Get the value of the specified key
        String expectedValue = jsonObject.getString(expectedKey);
        if (!StringUtils.hasText(expectedValue)) {
            return null;
        }
        // Parse the JSON string into the specified type
        return JSON.parseObject(expectedValue, t);
    }
}
