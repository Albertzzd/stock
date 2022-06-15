package com.itheima.stock.vo.req;

import lombok.Data;

@Data
public class UserReqVo {

    private Integer pageNum;

    private Integer pageSize;


    private String username;

    private String nickname;

    private String startTime;

    private String endTime;



}