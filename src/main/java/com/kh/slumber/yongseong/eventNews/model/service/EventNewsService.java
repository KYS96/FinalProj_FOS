package com.kh.slumber.yongseong.eventNews.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNews;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsFrontImages;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsOnly;

public interface EventNewsService {

  ArrayList<MarketEventNewsOnly> getEventNewsMainList();

  ArrayList<MarketEventNewsFrontImages> getFrontImagesList(ArrayList<Integer> eventNo);

  int selectEventNewsListCountDetail(Integer eventNo);

  ArrayList<MarketEventNews> selectEventNewsListDetail(Integer eventNo, PageInfo productPi);

  int selectSearchEventListCountDetail(HashMap<String, Object> data);

  ArrayList<MarketEventNews> selectSearchEventListDetail(PageInfo productPi, HashMap<String, Object> data);

  int selectOnlyEventNewsSearchListCount(Properties prop);

  ArrayList<MarketEventNewsOnly> selectOnlyEventNewsSearchList(PageInfo pi, Properties prop);

  ArrayList<MarketEventNews> selectEventNewsListDetail(Integer num, Integer eventNo, PageInfo pi);

  int selectEventNewsByDate(String search);

  ArrayList<MarketEventNews> selectEventNewsListByDate(PageInfo pi, String search);

}
