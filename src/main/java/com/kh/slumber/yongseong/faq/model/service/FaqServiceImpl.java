package com.kh.slumber.yongseong.faq.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.faq.model.dao.FaqDAO;
import com.kh.slumber.yongseong.faq.model.vo.MarketFAQ;

@Service
public class FaqServiceImpl implements FaqService {

  @Autowired
  private FaqDAO fDAO;

  @Override
  public int selectFaqListCount() {
    return fDAO.selectFaqListCount();
  }

  @Override
  public ArrayList<MarketFAQ> selectFaqList(PageInfo pi) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return fDAO.selectFaqList(rowBounds);
  }

  @Override
  public int faqInsert(MarketFAQ faq) {
    return fDAO.faqInsert(faq);
  }

  @Override
  public int faqDelete(String fNo) {
    return fDAO.faqDelete(fNo);
  }

  @Override
  public int selectFaqSearchListCount(String faqSearchBar) {
    return fDAO.selectFaqSearchListCount(faqSearchBar);
  }

  @Override
  public ArrayList<MarketFAQ> selectFaqSearchList(PageInfo pi, String faqSearchBar) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return fDAO.selectFaqSearchList(faqSearchBar, rowBounds);
  }

  @Override
  public MarketFAQ faqUpdate(int faqNo) {
    return fDAO.faqUpdate(faqNo);
  }

  @Override
  public int faqUpdateConfirm(HashMap<Object, Object> data) {
    return fDAO.faqUpdateConfirm(data);
  }

}
