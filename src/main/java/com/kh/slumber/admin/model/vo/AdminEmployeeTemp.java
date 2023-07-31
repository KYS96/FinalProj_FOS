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
public class AdminEmployeeTemp {
    private String employeeNo;
    private String employeeId;
    private String employeePwd;
    private String employeeName;
    private String employeePhone;
    private String employeeStatus;
}
