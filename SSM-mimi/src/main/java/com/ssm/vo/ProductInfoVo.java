package com.ssm.vo;

import lombok.Data;

@Data
public class ProductInfoVo {

    private String  pname;

    private Integer typeid;

    private Double lprice;

    private Double hprice;

    private Integer page = 1;

}
