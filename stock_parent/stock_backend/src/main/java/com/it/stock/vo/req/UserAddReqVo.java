package com.it.stock.vo.req;

import lombok.Data;


@Data
public class UserAddReqVo {
  private String  username;
  private String  password;
  private String  phone;
  private String  email;
  private String  nickName;
  private String  realName;
  private Integer  sex;
  private Integer  createWhere;
  private Integer  status;
}