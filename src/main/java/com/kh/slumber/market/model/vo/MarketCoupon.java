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
public class MarketCoupon {
    
    private String couponIssuanceNo;
    private String couponNo;
    private String couponStartDate;
    private String couponEndDate;
    private String couponUseDate;
    private String couponStatus;
    private String couponName;
    private int couponDiscountRate;
    private String couponCondition;
    private String couponUseCondition;
}
