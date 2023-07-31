package com.kh.slumber.yongseong.eventNews.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.eventNews.model.dao.EventNewsDAO;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNews;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsFrontImages;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsOnly;

@Service
public class EventNewsServiceImpl implements EventNewsService {

  @Autowired
  private EventNewsDAO eDAO;

  @Override
  public ArrayList<MarketEventNewsOnly> getEventNewsMainList() {
    return eDAO.getEventNewsMainList();
  }

  @Override
  public ArrayList<MarketEventNewsFrontImages> getFrontImagesList(ArrayList<Integer> eventNo) {
    return eDAO.getFrontImagesList(eventNo);
  }

  @Override
  public int selectEventNewsListCountDetail(Integer eventNo) {
    return eDAO.selectEventNewsListCountDetail(eventNo);
  }

  @Override
  public ArrayList<MarketEventNews> selectEventNewsListDetail(Integer eventNo, PageInfo pi) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return eDAO.selectEventNewsListDetail(eventNo, rowBounds);
  }

  @Override
  public int selectSearchEventListCountDetail(HashMap<String, Object> data) {
    return eDAO.selectSearchEventListCountDetail(data);
  }

  @Override
  public ArrayList<MarketEventNews> selectSearchEventListDetail(PageInfo productPi, HashMap<String, Object> data) {
    int offset = (productPi.getCurrentPage() - 1) * productPi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, productPi.getBoardLimit());
    return eDAO.selectSearchEventListDetail(data, rowBounds);
  }

  @Override
  public int selectOnlyEventNewsSearchListCount(Properties prop) {
    return eDAO.selectOnlyEventNewsSearchListCount(prop);
  }

  @Override
  public ArrayList<MarketEventNewsOnly> selectOnlyEventNewsSearchList(PageInfo pi, Properties prop) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return eDAO.selectOnlyEventNewsSearchList(prop, rowBounds);
  }

  @Override
  public ArrayList<MarketEventNews> selectEventNewsListDetail(Integer num, Integer eventNo, PageInfo pi) {

    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

    HashMap<String, Integer> data = new HashMap<>();
    data.put("num", num);
    data.put("eventNo", eventNo);
    return eDAO.selectEventNewsListDetail(data, rowBounds);
  }

  @Override
  public int selectEventNewsByDate(String search) {
    return eDAO.selectEventNewsByDate(search);
  }

  @Override
  public ArrayList<MarketEventNews> selectEventNewsListByDate(PageInfo pi, String search) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return eDAO.selectEventNewsListByDate(search, rowBounds);
  }

}
