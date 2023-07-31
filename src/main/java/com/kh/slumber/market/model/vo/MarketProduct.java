package com.kh.slumber.market.model.vo;

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
public class MarketProduct{
    private String productNo;
    private String productCategoryNo;
    private String productCategoryName;
    private String employeeNo;
    private String productName;
    private int productPrice;
    private String productResistDate;
    private String productUpdate;
    private int productStock;
    private String productSales;
    private String productImage;
    private String productStatus;
    private String productExplain;
    private double productPoint;
    private String eventTitle; //진행중인 행사 명
    private int eventCode; //행사코드 (ERD참고)
}
