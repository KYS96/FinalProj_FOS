package com.kh.slumber.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.admin.model.dao.AdminDAOTempStatistic;
import com.kh.slumber.admin.model.vo.AdminProduct;
import com.kh.slumber.admin.model.vo.AdminProductImages;
import com.kh.slumber.admin.model.vo.AdminStatisticBoardTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticProductChartTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticSpendingInfoTemp;
import com.kh.slumber.common.model.vo.PageInfo;

@Service
public class AdminServiceTempStatistic {

  @Autowired
  private AdminDAOTempStatistic atsDAO;

  public int getNewMemberTotal() {
    return atsDAO.getNewMemberTotal();
  }

  public int getDisabledMemberTotal() {
    return atsDAO.getDisabledMemberTotal();
  }

  public int getEntireMemberTotal() {
    return atsDAO.getEntireMemberTotal();
  }

  public int getListCount() {
    return atsDAO.getListCount();
  }

  public int getMemberSpendingDatasListCount() {
    return atsDAO.getMemberSpendingDatasListCount();
  }

  public ArrayList<AdminStatisticSpendingInfoTemp> getMemberSpendingDatas(PageInfo pi) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return atsDAO.getMemberSpendingDatas(rowBounds);
  }

  public ArrayList<AdminStatisticBoardTemp> getBoardViewRanking(PageInfo pi) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return atsDAO.getBoardViewRanking(rowBounds);
  }

  public AdminStatisticBoardTemp goToBoard(Properties prop) {
    return atsDAO.goToBoard(prop);
  }

  public int getProductSalesListCount() {
    return atsDAO.getProductSalesListCount();
  }

  public ArrayList<AdminProduct> getProductSalesList(PageInfo pi) {
    int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
    RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
    return atsDAO.getProductSalesList(rowBounds);
  }

  public AdminProduct getProductInfo(String productNo) {
    return atsDAO.getProductInfo(productNo);
  }

  public AdminProductImages getFrontImage(String productNo) {
    return atsDAO.getFrontImage(productNo);
  }

  public ArrayList<AdminProductImages> getSearchImages(HashMap<String, String> map) {
    return atsDAO.getSearchImages(map);
  }

  public ArrayList<AdminStatisticProductChartTemp> getSalesChartDatas() {
    return atsDAO.getSalesChartDatas();
  }

}
