package com.kh.slumber.admin.model.vo;

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
public class AdminStatisticProductChartTemp {
  private String productCategoryNo;
  private String productCategoryName;
  private int sumProductSales;
}
