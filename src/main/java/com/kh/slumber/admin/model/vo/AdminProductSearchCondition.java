package com.kh.slumber.admin.model.vo;

import java.util.ArrayList;

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
public class AdminProductSearchCondition {
    private String selectProductNo;
    private String category;
    private String query;
    private String order;
    private ArrayList<String> productCategoryNoList;
    private String selectResistDate;
    private String selectProductStatus;
    private String productResistDateStart;
    private String productResistDateEnd;
    private String selectUpdate;
    private String productUpdateStart;
    private String productUpdateEnd;
}
