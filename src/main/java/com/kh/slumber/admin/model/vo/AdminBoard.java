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

public class AdminBoard {
    private String boardNo;
    private String memberNo;
    private String boardTitle;
    private String boardLikes;
    private String boardDislikes;
    private String boardReports;
    private String boardViews;
    private String boardType;
    private String boardEnrollDate;
    private String boardModifyDate;
    private String boardIsdeleted;
    private String boardIshidden;
    private String memberNickname;
    private String memberIsBlacklist;
}
