package com.kh.slumber.yongseong.faq.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.yongseong.faq.model.vo.MarketFAQ;

@Mapper
public interface FaqDAO {

  int selectFaqListCount();

  ArrayList<MarketFAQ> selectFaqList(RowBounds rowBounds);

  int faqInsert(MarketFAQ faq);

  int faqDelete(String fNo);

  int selectFaqSearchListCount(String faqSearchBar);

  ArrayList<MarketFAQ> selectFaqSearchList(String faqSearchBar, RowBounds rowBounds);

  MarketFAQ faqUpdate(int faqNo);

  int faqUpdateConfirm(HashMap<Object, Object> data);

}
