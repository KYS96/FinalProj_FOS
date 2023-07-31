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

public class AdminCouponIssuance {
    private String couponIssuanceNo;
    private String couponNo;
    private String memberNo;
    private String couponStartDate;
    private String couponEndDate;
    private String couponUsedate;
    private String couponStatus;
    private String couponIsChecked;
}
