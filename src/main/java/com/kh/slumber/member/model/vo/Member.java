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
public class Member {

    private String memberNo;
    private String memberId;
    private String memberPassword;
    private String memberNickname;
    private String memberName;
    private String memberPhone;
    private Date memberEnrollDate;
    private String memberStatus;
    private String memberIsBlacklist;
    private String memberAddr;
    private String memberDetailAddr;

}
