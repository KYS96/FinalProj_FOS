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
public class AdminStatisticBoardTemp {
  private String sortion;
  private String boardType;
  private int boardNo;
  private String boardTitle;
  private String memberId;
  private String boardIsdeleted;
  private int views;
}
