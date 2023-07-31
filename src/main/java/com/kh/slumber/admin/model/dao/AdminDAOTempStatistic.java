package com.kh.slumber.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.admin.model.vo.AdminProduct;
import com.kh.slumber.admin.model.vo.AdminProductImages;
import com.kh.slumber.admin.model.vo.AdminStatisticBoardTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticProductChartTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticSpendingInfoTemp;

@Mapper
public interface AdminDAOTempStatistic {

  int getNewMemberTotal();

  int getDisabledMemberTotal();

  int getEntireMemberTotal();

  int getListCount();

  int getMemberSpendingDatasListCount();

  ArrayList<AdminStatisticSpendingInfoTemp> getMemberSpendingDatas(RowBounds rowBounds);

  ArrayList<AdminStatisticBoardTemp> getBoardViewRanking(RowBounds rowBounds);

  AdminStatisticBoardTemp goToBoard(Properties prop);

  int getProductSalesListCount();

  ArrayList<AdminProduct> getProductSalesList(RowBounds rowBounds);

  AdminProduct getProductInfo(String productNo);

  AdminProductImages getFrontImage(String productNo);

  ArrayList<AdminProductImages> getSearchImages(HashMap<String, String> map);

  ArrayList<AdminStatisticProductChartTemp> getSalesChartDatas();

}
