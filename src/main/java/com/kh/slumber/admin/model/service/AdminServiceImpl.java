package com.kh.slumber.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.admin.model.dao.AdminDAO;
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

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDAO aDAO;

    @Override
    public int enrollProduct(AdminProduct p) {
        return aDAO.enrollProduct(p);
    }

    @Override
    public int enrollProductImages(ArrayList<HashMap<String, String>> list) {
        return aDAO.enrollProductImages(list);
    }


    @Override
    public int getSearchListCount(HashMap<String, Object> map) {
        return aDAO.getSearchListCount(map);
    }

    @Override
    public ArrayList<AdminProduct> searchProductAdmin(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.searchProductAdmin(map, rowBounds);
    }

    @Override
    public int changeProductStatusToN(String productNo) {
        return aDAO.changeProductStatusToN(productNo);
    }

    @Override
    public int changeProductStatusToY(String productNo) {
        return aDAO.changeProductStatusToY(productNo);
    }

    @Override
    public int changeProductCategory(HashMap<String, String> map) {
        return aDAO.changeProductCategory(map);
    }

    @Override
    public int changeProductName(HashMap<String, String> map) {
        return aDAO.changeProductName(map);
    }

    @Override
    public int changeProductPrice(HashMap<String, String> map) {
        return aDAO.changeProductPrice(map);
    }

    @Override
    public int changeProductStock(HashMap<String, String> map) {
        return aDAO.changeProductStock(map);
    }

    @Override
    public AdminProduct searchProductNo(String productNo) {
        return aDAO.searchProductNo(productNo);
    }

    @Override
    public AdminProductImages searchFrontImage(String productNo) {
        return aDAO.searchFrontImage(productNo);
    }

    @Override
    public ArrayList<AdminProductImages> searchImages(HashMap<String, String> map) {
        return aDAO.searchImages(map);
    }

    @Override
    public int deleteImage(ArrayList<String> list) {
        return aDAO.deleteImage(list);
    }

    @Override
    public int updateProduct(AdminProduct p) {
        return aDAO.updateProduct(p);
    }

    @Override
    public String selectProductNo(String productImage) {
        return aDAO.selectProductNo(productImage);
    }

    @Override
    public int enrollCoupon(AdminCoupon c) {
        return aDAO.enrollCoupon(c);
    }

    @Override
    public int getSearchCouponListCount(HashMap<String, Object> map) {
        return aDAO.getSearchCouponListCount(map);
    }

    @Override
    public ArrayList<AdminCoupon> searchCoupon(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.searchCoupon(map, rowBounds);
    }

    @Override
    public ArrayList<AdminCoupon> getCouponList() {
        return aDAO.getCouponList();
    }

    @Override
    public int downloadCoupon(HashMap<String, Object> map) {
        return aDAO.downloadCoupon(map);
    }

    @Override
    public int changeCouponUseCondition(HashMap<String, String> map) {
        return aDAO.changeCouponUseCondition(map);
    }

    @Override
    public int changeCouponDiscountRate(HashMap<String, String> map) {
        return aDAO.changeCouponDiscountRate(map);
    }

    @Override
    public int changeCouponName(HashMap<String, String> map) {
        return aDAO.changeCouponName(map);
    }

    @Override
    public int changeCouponContent(HashMap<String, String> map) {
        return aDAO.changeCouponContent(map);
    }

    @Override
    public int changeCouponCondition(HashMap<String, String> map) {
        return aDAO.changeCouponCondition(map);
    }

    @Override
    public int changeCouponOpenClose(HashMap<String, String> map) {
        return aDAO.changeCouponOpenClose(map);
    }

    @Override
    public int changeCouponPeriodType(HashMap<String, String> map) {
        return aDAO.changeCouponPeriodType(map);
    }

    @Override
    public int changeCouponPeriod(HashMap<String, String> map) {
        return aDAO.changeCouponPeriod(map);
    }

    @Override
    public ArrayList<AdminCouponIssuance> getCouponIssuanceList(String memberNo) {
        return aDAO.getCouponIssuanceList(memberNo);
    }

    @Override
    public int enrollEvent(AdminEvent e) {
        return aDAO.enrollEvent(e);
    }

    @Override
    public int enrollFrontImage(HashMap<String, String> map) {
        return aDAO.enrollFrontImage(map);
    }

    @Override
    public int getSearchEventListCount(HashMap<String, Object> map) {
        return aDAO.getSearchEventListCount(map);
    }

    @Override
    public ArrayList<AdminEvent> getSearchEventList(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.getSearchEventList(map, rowBounds);
    }

    @Override
    public int getSearchBoardListCount(HashMap<String, Object> map) {
        return aDAO.getSearchBoardListCount(map);
    }

    @Override
    public ArrayList<AdminBoard> getSearchBoardList(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.getSearchBoardList(map, rowBounds);
    }

    @Override
    public int getSearchReplyListCount(HashMap<String, Object> map) {
        return aDAO.getSearchReplyListCount(map);
    }

    @Override
    public ArrayList<AdminReply> getSearchReplyList(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.getSearchReplyList(map, rowBounds);
    }

    @Override
    public int updateTable(HashMap<String, Object> map) {
        return aDAO.updateTable(map);
    }

    @Override
    public int getSearchProductOrderListCount(HashMap<String, Object> map) {
        return aDAO.getSearchProductOrderListCount(map);
    }

    @Override
    public ArrayList<AdminProductOrder> getSearchProductOrderList(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.getSearchProductOrderList(map, rowBounds);
    }

    @Override
    public ArrayList<AdminProductOrder> getCartList(String cartNo) {
        return aDAO.getCartList(cartNo);
    }

    @Override
    public int getSearchQuestionBoardListCount(HashMap<String, Object> map) {
        return aDAO.getSearchQuestionBoardListCount(map);
    }

    @Override
    public ArrayList<AdminQuestionBoard> getSearchQuestionBoardList(PageInfo pi, HashMap<String, Object> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
        return aDAO.getSearchQuestionBoardList(map, rowBounds);
    }

    @Override
    public AdminQuestionBoard getQuestionBoardDetail(String questionNo) {
        return aDAO.getQuestionBoardDetail(questionNo);
    }

    @Override
    public int enrollEventProducts(HashMap<String, Object> map) {
        return aDAO.enrollEventProducts(map);
    }

    @Override
    public AdminEvent getEvent(String eventNo) {
        return aDAO.getEvent(eventNo);
    }

    @Override
    public ArrayList<String> getEventProductNoList(String eventNo) {
        return aDAO.getEventProductNoList(eventNo);
    }

    @Override
    public ArrayList<AdminProduct> getEventProductList(ArrayList<String> eventProductNoList) {
        return aDAO.getEventProductList(eventProductNoList);
    }

    @Override
    public int updateEventDetail(AdminEvent e) {
        return aDAO.updateEventDetail(e);
    }

    @Override
    public int deleteEventProducts(HashMap<String, Object> map) {
        return aDAO.deleteEventProducts(map);
    }

    @Override
    public ArrayList<String> checkEventProductDuplication(HashMap<String, Object> map) {
        return aDAO.checkEventProductDuplication(map);
    }

    @Override
    public int changeCouponStatus(HashMap<String, String> map) {
        return aDAO.changeCouponStatus(map);
    }

   




    
}
