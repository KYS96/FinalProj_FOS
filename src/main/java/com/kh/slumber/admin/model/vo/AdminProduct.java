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

public class AdminProduct{
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
    private String productPoint;
}