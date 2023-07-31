package com.kh.slumber.yongseong.faq.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.faq.model.vo.MarketFAQ;

public interface FaqService {

  int selectFaqListCount();

  ArrayList<MarketFAQ> selectFaqList(PageInfo pi);

  int faqInsert(MarketFAQ faq);

  int faqDelete(String fNo);

  int selectFaqSearchListCount(String faqSearchBar);

  ArrayList<MarketFAQ> selectFaqSearchList(PageInfo pi, String faqSearchBar);

  MarketFAQ faqUpdate(int faqNo);

  int faqUpdateConfirm(HashMap<Object, Object> data);

}
