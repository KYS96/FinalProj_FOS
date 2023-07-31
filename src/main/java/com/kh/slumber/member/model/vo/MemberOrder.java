package com.kh.slumber.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberOrder {
    private String productImage;
    private String productName;
    private double productPrice;
    private String productNo;
    private String orderNo;
    private int orderAmount;
    private Date orderDeliveryDate;
    private String orderDeliveryCourier;
    private String orderDeliveryNo;
    private String orderType;
    private double orderPrice;
    private Date orderDate;
    private String orderAddr;
    private String orderDetailAddr;
    private String couponUse;
    private double couponDiscountRate;
    private String couponName;
    private String couponCondition;
    private String couponUseCondition;
    private String cartNo;
    private String eventTitle;
    private double eventCode;
    private Date eventEndDate;
    private String eventContent;
}
