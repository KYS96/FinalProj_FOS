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
public class MemberBoard {
    private String boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardLikes;
    private String boardDislikes;
    private String boardReports;
    private String boardViews;
    private String boardType;
    private Date boardEnrollDate;
    private Date boardModifyDate;
    private String boardIsDeleted;
    private String boardIsHidden;
    private int replyCount;
}
