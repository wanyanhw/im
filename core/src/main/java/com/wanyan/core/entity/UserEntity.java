package com.wanyan.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户注册表
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户注册编号
     */
      @TableField("user_no")
    private String userNo;

      /**
     * 用户登录密码
     */
      @TableField("password")
    private String password;

      /**
     * 删除标识位
     */
      @TableField("deleted")
    private Boolean deleted;


}
