package com.kh.slumber.market.model.vo;

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
public class MarketProductOrder {
    
    private String orderNo; //주문번호
    private String memberNo; //회원번호
    private String memberName;
    private String memberPhone;
    private String productNo; //주문 상품번호
    private String productName; //주문 상품명
    private String orderDate; //주문 일자
    private String orderAmount; //주문 수량
    private String orderPrice; //총 주문 금액
    private String productImage; //상품 이미지
    private String orderAddr; //ORDER_DETAIL_ADDR
    private String orderDetailAddr;
    private String couponIssuanceNo; //쿠폰발급번호
    private String cartNo;
    private String orderMemo;
    private String orderType;

}
