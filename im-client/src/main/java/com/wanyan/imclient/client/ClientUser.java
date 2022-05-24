package com.wanyan.imclient.client;

import lombok.Data;

/**
 * @author wanyanhw
 * @date 2022/5/24 14:32
 */
@Data
public class ClientUser {
    private String name;
    private String channelId;
    private Integer messageType = 1;
}
