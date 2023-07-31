package com.kh.slumber.yongseong.notice.model.vo;

import java.util.Date;

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
public class MarketNotice {
  private int employeeBoardNo;
  private String employeeBoardTitle;
  private Date employeeBoardUpdate;
  private int employeeBoardViews;
  private int employeeBoardType;
  private String employeeBoardStatus;
  private String employeeBoardContent;
}
