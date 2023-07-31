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
public class AdminStatisticSpendingInfoTemp {
  private int memberNo;
  private String memberId;
  private String memberName;
  private String memberPhone;
  private String memberAddr;
  private int totalSpending;
}
