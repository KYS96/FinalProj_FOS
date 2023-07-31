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
public class MarketQnA {
    
    private String questionNo;
    private String memberNo;
    private String memberNickName;
    private String productNo;
    private String questionTitle;
    private String questionContent;
    private Date questionUpdateDate;
    private String questionAnswer;
}
