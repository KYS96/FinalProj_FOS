package com.kh.slumber.yongseong.eventNews.model.vo;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DB에서 event 테이블, product테이블, event_product 테이블을 조인한 것들을 받아오는 vo
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MarketEventNews {
  private int eventNo;
  private int productNo;//
  private int employeeNo;//
  private String productCategoryNo;
  private String productName;//
  private int productPrice;//
  private Date productResistDate;//
  private Date eventStartDate;//
  private Date eventEndDate;//
  private int productStock;//
  private String productStatus;
  private int eventCode;//
  private String eventTitle;//
  private String eventActivity;//
  private String eventContent;//
  private int discountedPrice;//
  private String productImage;//
}
