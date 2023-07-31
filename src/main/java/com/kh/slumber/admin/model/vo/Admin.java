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
public class Admin {
    private String adminNo;
    private String adminId;
    private String adminPwd;
    private String adminName;
    private String adminPhone;
    private String adminStatus;
}
