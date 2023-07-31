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

public class AdminEvent {
    private String eventNo;
    private String employeeNo;
    private String eventCode;
    private String eventTitle;
    private String eventStartDate;
    private String eventEndDate;
    private String eventActivity;
    private String eventContent;
    private String frontImage;
}
