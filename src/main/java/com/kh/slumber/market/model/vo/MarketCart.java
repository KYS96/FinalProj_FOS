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
public class MarketCart {

    private String productNo; // 상품 번호
    private String productName; // 상품 명
    private int productPrice; // 상품 가격
    private String eventTitle; // 이벤트 명
    private int eventCode; // 이벤트 코드
    private String amount; // 상품 개수
    private String productImage; // 상품 이미지
    private String total;

    
    
}
