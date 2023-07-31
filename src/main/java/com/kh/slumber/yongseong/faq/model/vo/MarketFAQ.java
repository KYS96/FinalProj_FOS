package com.kh.slumber.yongseong.faq.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarketFAQ {
  private Integer faqNo;
  private String faqTitle;
  private String faqContent;
  Date faqEnrollDate;
  Date faqModifyDate;
}
