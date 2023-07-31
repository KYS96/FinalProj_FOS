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
public class MemberContact {
    private String questionNo;
    private String questionTitle;
    private Date questionUpdateDate;
    private String questionContent;
    private String questionAnswer;
    private String productNo;
    private String productName;
    private String productImage;
}
