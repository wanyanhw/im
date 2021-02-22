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
 * 用户注册详情
 * </p>
 *
 * @author wanyanhw
 * @since 2021-02-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("user_detail")
public class UserDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户ID
     */
      @TableField("user_id")
    private Integer userId;

      /**
     * 注册名称
     */
      @TableField("name")
    private String name;

      /**
     * 性别：1-男，2-女，0-未知
     */
      @TableField("sex")
    private Integer sex;

      /**
     * 删除标识位
     */
      @TableField("deleted")
    private Boolean deleted;

      /**
     * 地址
     */
      @TableField("address")
    private String address;

      /**
     * 联系方式
     */
      @TableField("phone")
    private String phone;


}
