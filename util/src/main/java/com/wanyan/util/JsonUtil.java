package com.wanyan.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wanyanhw
 * @date 2021/2/5 16:55
 */
public class JsonUtil {

    public static <T> T parseJson(String data, Class<T> clazz) {
        return JSONObject.parseObject(data, clazz);
    }

}
