package com.kh.slumber.yongseong.eventNews.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNews;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsFrontImages;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsOnly;

@Mapper
public interface EventNewsDAO {

  ArrayList<MarketEventNewsOnly> getEventNewsMainList();

  ArrayList<MarketEventNewsFrontImages> getFrontImagesList(ArrayList<Integer> eventNo);

  int selectEventNewsListCountDetail(Integer eventNo);

  ArrayList<MarketEventNews> selectEventNewsListDetail(Integer eventNo, RowBounds rowBounds);

  int selectSearchEventListCountDetail(HashMap<String, Object> data);

  ArrayList<MarketEventNews> selectSearchEventListDetail(HashMap<String, Object> data, RowBounds rowBounds);

  int selectOnlyEventNewsSearchListCount(Properties prop); // search, category

  ArrayList<MarketEventNewsOnly> selectOnlyEventNewsSearchList(Properties prop, RowBounds rowBounds);

  ArrayList<MarketEventNews> selectEventNewsListDetail(HashMap<String, Integer> data, RowBounds rowBounds);

  int selectEventNewsByDate(String search);

  ArrayList<MarketEventNews> selectEventNewsListByDate(String search, RowBounds rowBounds);

}
