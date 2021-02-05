package com.wanyan.core.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wanyanhw
 * @date 2021/2/5 16:38
 */
@Data
public class MessageModel {
    private int type;
    private String content;
    private String from;
    private String to;
    private LocalDateTime time;
}
