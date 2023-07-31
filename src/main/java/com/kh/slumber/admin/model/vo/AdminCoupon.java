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

public class AdminCoupon {
    private String couponNo;
    private int employeeNo;
    private String couponName;
    private int couponDiscountRate;
    private String couponContent;
    private String couponCondition;
    private String couponPeriod;
    private String couponOpenDate;
    private String couponCloseDate;
    private String couponUseCondition;
    private String couponPeriodType;
    private String couponStatus;
}
