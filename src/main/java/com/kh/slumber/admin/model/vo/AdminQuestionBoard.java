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

public class AdminQuestionBoard{
    private String questionNo;
    private String memberNo;
    private String questionTitle;
    private String questionUpdateDate;
    private String questionContent;
    private String questionAnswer;
    private String productNo;
    private String memberName;
    private String productName;
}