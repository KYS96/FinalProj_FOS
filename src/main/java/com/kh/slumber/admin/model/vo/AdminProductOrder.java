package com.kh.slumber.admin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AdminProductOrder{
    private String orderNo;
    private String memberNo;
    private String productNo;
    private String orderDate;
    private String orderAmount;
    private String orderType;
    private String orderDeliveryDate;
    private String orderDeliveryNo;
    private String orderDeliveryCourier;
    private String orderAddr;
    private String orderDetailAddr;
    private String couponUse;
    private String couponIssuanceNo;
    private String orderPrice;
    private String cartNo;
    private String orderMemo;
    private String memberName;
    private String memberPhone;
    private String productName;
}