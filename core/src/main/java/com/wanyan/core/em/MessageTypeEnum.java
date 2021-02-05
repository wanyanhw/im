package com.wanyan.core.em;

import com.wanyan.core.exception.EnumNoParamException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanyanhw
 * @date 2021/2/5 16:41
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    PERSONAL(1, "个人"),
    GROUP(2, "群");
    int code;
    String des;

    public static MessageTypeEnum getByCode(int code) throws EnumNoParamException {
        for (MessageTypeEnum anEnum : MessageTypeEnum.values()) {
            if (anEnum.getCode() == code) {
                return anEnum;
            }
        }
        throw new EnumNoParamException("消息类型必须是个人或群组");
    }
}
