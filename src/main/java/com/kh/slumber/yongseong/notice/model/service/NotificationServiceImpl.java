package com.kh.slumber.yongseong.notice.model.service;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.notice.model.dao.NotificationDAO;
import com.kh.slumber.yongseong.notice.model.vo.MarketNotice;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationDAO nDAO;

  @Override
  public int selectNoticeSearchListCount(Properties prop) {
    return nDAO.selectNoticeSearchListCount(prop);
  }

  @Override
  public ArrayList<MarketNotice> selectNoticeSearchList(PageInfo pi, Properties prop) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return nDAO.selectNoticeSearchList(prop, rowBounds);
  }

  @Override
  public MarketNotice selectDetailNoticePage(int empBoardNo) {
    return nDAO.selectDetailNoticePage(empBoardNo);
  }

  @Override
  public int noticeInsert(MarketNotice mn) {
    return nDAO.noticeInsert(mn);
  }

  @Override
  public int noticeDelete(int employeeBoardNo) {
    return nDAO.noticeDelete(employeeBoardNo);
  }

  @Override
  public int noticeUpdatePopupConfirm(MarketNotice mn) {
    return nDAO.noticeUpdatePopupConfirm(mn);
  }

  @Override
  public int noticeAddCount(int empBoardNo) {
    return nDAO.noticeAddCount(empBoardNo);
  }

}
