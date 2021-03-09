package com.wanyan.controller.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyanhw
 * @date 2021/2/11 11:53
 */
@ApiModel(value = "AccountBaseModel", description = "账户基本信息")
@Data
public class AccountBaseModel {
    @ApiModelProperty("用户账号")
    private String userNo;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private int sex;
    @ApiModelProperty("住址")
    private String address;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
}
