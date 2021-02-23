package com.wanyan.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wanyanhw
 * @date 2021/2/23 14:08
 */
@Data
@Accessors(chain = true)
public class BaseResponse<T> {
    private int code;
    private String msg = "ok";
    private T data;
}
