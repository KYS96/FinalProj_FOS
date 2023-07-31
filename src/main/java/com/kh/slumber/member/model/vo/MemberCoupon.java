package com.kh.slumber.member.model.vo;

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
public class MemberCoupon {
    private String couponNo;
    private String couponName;
    private String couponDiscountRate;
    private String couponContent;
    private String couponCondition;
    private String couponPeriod;
    private Date couponOpenDate;
    private String couponCloseDate;
    private String couponUseCondition;
    private String couponPeriodType;
    private String couponIssuanceNo;
    private Date couponStartDate;
    private String couponEndDate;
    private Date couponUseDate;
    private String couponIsChecked;
}
