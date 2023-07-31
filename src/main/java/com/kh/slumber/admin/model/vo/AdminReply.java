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

public class AdminReply {
    private String replyNo;
    private String memberNo;
    private String boardNo;
    private String replyContent;
    private String replyLikes;
    private String replyDislikes;
    private String replyReports;
    private String replyEnrollDate;
    private String replyModifyDate;
    private String replyIsdeleted;
    private String replyIshidden;
    private String memberNickname;
    private String memberIsBlacklist;
}
