package com.kh.slumber.yongseong.notice.model.service;

import java.util.ArrayList;
import java.util.Properties;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.notice.model.vo.MarketNotice;

public interface NotificationService {

  int selectNoticeSearchListCount(Properties prop);

  ArrayList<MarketNotice> selectNoticeSearchList(PageInfo pi, Properties prop);

  MarketNotice selectDetailNoticePage(int empBoardNo);

  int noticeInsert(MarketNotice mn);

  int noticeDelete(int employeeBoardNo);

  int noticeUpdatePopupConfirm(MarketNotice mn);

  int noticeAddCount(int empBoardNo);

}
