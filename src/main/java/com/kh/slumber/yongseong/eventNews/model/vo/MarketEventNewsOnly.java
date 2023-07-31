package com.kh.slumber.yongseong.eventNews.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 오직 EVENT 테이블의 정보만 받아오기
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MarketEventNewsOnly {
  private int eventNoOnly;
  private int eventCodeOnly;//
  private String eventTitleOnly;//
  private Date eventStartDateOnly;//
  private Date eventEndDateOnly;//
  private String eventActivityOnly;//
  private String eventContentOnly;//
}
