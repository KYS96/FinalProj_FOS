package com.kh.slumber.yongseong.notice.model.dao;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.yongseong.notice.model.vo.MarketNotice;

@Mapper
public interface NotificationDAO {

  MarketNotice selectDetailNoticePage(int empBoardNo);

  int noticeInsert(MarketNotice mn);

  int noticeDelete(int employeeBoardNo);

  int noticeUpdatePopupConfirm(MarketNotice mn);

  int selectNoticeSearchListCount(Properties prop);

  ArrayList<MarketNotice> selectNoticeSearchList(Properties prop, RowBounds rowBounds);

  int noticeAddCount(int empBoardNo);

}
