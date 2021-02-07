package com.wanyan.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-07
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("message")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 消息类型，1-个人，2-群组
     */
      @TableField("content_type")
    private Integer contentType;

      /**
     * 来源
     */
      @TableField("content_from")
    private String contentFrom;

      /**
     * 到达
     */
      @TableField("content_to")
    private String contentTo;

      /**
     * 发送内容
     */
      @TableField("content")
    private String content;

      /**
     * 发送时间
     */
      @TableField("send_time")
    private LocalDateTime sendTime;

    @TableField("deleted")
    private Boolean deleted;


}
