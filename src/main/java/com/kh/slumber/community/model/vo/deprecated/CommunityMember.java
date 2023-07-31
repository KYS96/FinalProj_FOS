package com.kh.slumber.community.model.vo.deprecated;
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
public class CommunityMember {
    private String memberNo;
    private String memberId;
    private String memberPassword;
    private String memberNickname;
    private String memberName;
    private String memberPhone;
    private String memberEnrollDate;
    private String memberStatus;
    private String memberIsBlacklist;
    private String memberAddr;
    private String memberDetailAddr;
}
