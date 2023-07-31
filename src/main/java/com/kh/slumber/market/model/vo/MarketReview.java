package com.kh.slumber.market.model.vo;

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
public class MarketReview {
    
    private String reviewNo; //리뷰번호
    private String orderNo; //주문번호
    // private String productNo; //주문번호대신 상품번호 (join)
    private String memberNickName; //리뷰작성자 닉네임
    private String reviewContent; //리뷰내용
    private int reviewPoint; //평점
    private Date reviewUpdateDate; //리뷰수정일
    // private String reviewThumbnail; //리뷰우측에띄울메인리뷰이미지
    private String firstImg;

}
