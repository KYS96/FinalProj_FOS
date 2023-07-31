package com.kh.slumber.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.slumber.admin.model.vo.AdminBoard;
import com.kh.slumber.admin.model.vo.AdminCoupon;
import com.kh.slumber.admin.model.vo.AdminCouponIssuance;
import com.kh.slumber.admin.model.vo.AdminEvent;
import com.kh.slumber.admin.model.vo.AdminProduct;
import com.kh.slumber.admin.model.vo.AdminProductImages;
import com.kh.slumber.admin.model.vo.AdminProductOrder;
import com.kh.slumber.admin.model.vo.AdminQuestionBoard;
import com.kh.slumber.admin.model.vo.AdminReply;
import com.kh.slumber.common.model.vo.PageInfo;

public interface AdminService {

    int enrollProduct(AdminProduct p);

    int enrollProductImages(ArrayList<HashMap<String, String>> list);

    int getSearchListCount(HashMap<String, Object> map);

    ArrayList<AdminProduct> searchProductAdmin(PageInfo pi, HashMap<String, Object> map);

    int changeProductStatusToN(String productNo);

    int changeProductStatusToY(String productNo);

    int changeProductCategory(HashMap<String, String> map);

    int changeProductName(HashMap<String, String> map);

    int changeProductPrice(HashMap<String, String> map);

    int changeProductStock(HashMap<String, String> map);

    AdminProduct searchProductNo(String productNo);

    AdminProductImages searchFrontImage(String productNo);

    ArrayList<AdminProductImages> searchImages(HashMap<String, String> map);

    int deleteImage(ArrayList<String> list);

    int updateProduct(AdminProduct p);

    String selectProductNo(String productImage);

    int enrollCoupon(AdminCoupon c);

    int getSearchCouponListCount(HashMap<String, Object> map);

    ArrayList<AdminCoupon> searchCoupon(PageInfo pi, HashMap<String, Object> map);

    ArrayList<AdminCoupon> getCouponList();

    int downloadCoupon(HashMap<String, Object> map);

    int changeCouponUseCondition(HashMap<String, String> map);

    int changeCouponDiscountRate(HashMap<String, String> map);

    int changeCouponName(HashMap<String, String> map);

    int changeCouponContent(HashMap<String, String> map);

    int changeCouponCondition(HashMap<String, String> map);

    int changeCouponOpenClose(HashMap<String, String> map);

    int changeCouponPeriodType(HashMap<String, String> map);

    int changeCouponPeriod(HashMap<String, String> map);

    ArrayList<AdminCouponIssuance> getCouponIssuanceList(String memberNo);

    int enrollEvent(AdminEvent e);

    int enrollFrontImage(HashMap<String, String> map);

    int getSearchEventListCount(HashMap<String, Object> map);

    ArrayList<AdminEvent> getSearchEventList(PageInfo pi, HashMap<String, Object> map);

    int getSearchBoardListCount(HashMap<String, Object> map);

    ArrayList<AdminBoard> getSearchBoardList(PageInfo pi, HashMap<String, Object> map);

    int getSearchReplyListCount(HashMap<String, Object> map);

    ArrayList<AdminReply> getSearchReplyList(PageInfo pi, HashMap<String, Object> map);

    int updateTable(HashMap<String, Object> map);

    int getSearchProductOrderListCount(HashMap<String, Object> map);

    ArrayList<AdminProductOrder> getSearchProductOrderList(PageInfo pi, HashMap<String, Object> map);

    ArrayList<AdminProductOrder> getCartList(String cartNo);

    int getSearchQuestionBoardListCount(HashMap<String, Object> map);

    ArrayList<AdminQuestionBoard> getSearchQuestionBoardList(PageInfo pi, HashMap<String, Object> map);

    AdminQuestionBoard getQuestionBoardDetail(String questionNo);

    int enrollEventProducts(HashMap<String, Object> map);

    AdminEvent getEvent(String eventNo);

    ArrayList<String> getEventProductNoList(String eventNo);

    ArrayList<AdminProduct> getEventProductList(ArrayList<String> eventProductNoList);

    int updateEventDetail(AdminEvent e);

    int deleteEventProducts(HashMap<String, Object> map);

    ArrayList<String> checkEventProductDuplication(HashMap<String, Object> map);

    int changeCouponStatus(HashMap<String, String> map);

}
