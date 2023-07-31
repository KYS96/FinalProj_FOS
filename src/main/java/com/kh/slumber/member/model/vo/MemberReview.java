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
public class MemberReview {
    private String reviewNo;
    private String orderNo;
    private String orderDate;
    private String orderAmount;
    private String orderPrice;
    private String reviewContent;
    private Date reviewUpdateDate;
    private String reviewPoint;
    private String productNo;
    private String productName;
    private String productImage;
    private String productAmount;
}
