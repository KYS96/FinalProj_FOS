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
public class MemberReply {
    private String replyNo;
    private String boardNo;
    private String memberNo;
    private String boardTitle;
    private String boardType;
    private String replyContent;
    private String replyLikes;
    private String replyDislikes;
    private String replyReports;
    private Date replyEnrollDate;
    private Date replyModifyDate;
    private String replyIsDeleted;
    private String replyIsHidden;
}
