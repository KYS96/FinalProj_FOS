package com.kh.slumber.market.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.market.model.dao.MarketDAO;
import com.kh.slumber.market.model.vo.MarketCoupon;
import com.kh.slumber.market.model.vo.MarketProduct;
import com.kh.slumber.market.model.vo.MarketProductAttm;
import com.kh.slumber.market.model.vo.MarketProductOrder;
import com.kh.slumber.market.model.vo.MarketQnA;
import com.kh.slumber.market.model.vo.MarketReview;

@Service
public class MarketServiceImpl implements MarketService{
    
    @Autowired
    private MarketDAO marketDAO;

    @Override
    public int getProductCount(HashMap<String, String> categoryMap) {
        return marketDAO.getProductCount(categoryMap);
    }

    @Override
    public ArrayList<MarketProduct> getProductList(PageInfo pi, HashMap<String, String> categoryMap) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return marketDAO.getProductList(categoryMap, rowBounds);
    }

    @Override
    public MarketProduct getProductDetail(String productNo) {
        return marketDAO.getProductDetail(productNo);
    }

    @Override
    public ArrayList<MarketProductAttm> getProductSubImgs(String productNo) {
        return marketDAO.getProductSubImgs(productNo);
    }

    @Override
    public ArrayList<MarketProductAttm> getProductDetailImg(String productNo) {
        return marketDAO.getProductDetailImg(productNo);
    }

    @Override
    public int getReviewCount(String productNo) {
        return marketDAO.getReviewCount(productNo);
    }

    @Override
    public ArrayList<MarketReview> getProductReviews(String productNo) {
        return marketDAO.getProductReviews(productNo);
    }

    @Override
    public ArrayList<MarketProductAttm> getReviewImgs(String productNo) {
        return marketDAO.getReviewImgs(productNo);
    }

    @Override
    public int getQnACount(String productNo) {
        return marketDAO.getQnACount(productNo);
    }

    @Override
    public ArrayList<MarketQnA> getQnAList(PageInfo pi, String productNo) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return marketDAO.getQnAList(productNo, rowBounds);
    }

    @Override
    public ArrayList<MarketCoupon> getUseableCoupons(int memberNo) {
        return marketDAO.getUseableCoupons(memberNo);
    }

    @Override
    public int insertNewOrder(MarketProductOrder newOrder) {
        return marketDAO.insertNewOrder(newOrder);
    }

    @Override
    public MarketProductOrder getNewOrderInfo(MarketProductOrder newOrder) {
        return marketDAO.getNewOrderInfo(newOrder);
    }

    @Override
    public void couponExpiration(String usedCoupon) {
        marketDAO.couponExpiration(usedCoupon);
    }

    @Override
    public int checkOrder(HashMap<String, String> map) {
        return marketDAO.checkOrder(map);
    }

    @Override
    public MarketProductOrder getRecentOrderInfo(HashMap<String, String> map) {
        return marketDAO.getRecentOrderInfo(map);
    }

    @Override
    public int writeReview(HashMap<String, String> map) {
        return marketDAO.writeReview(map);
    }

    @Override
    public int insertReviewImgs(ArrayList<HashMap<String, String>> list) {
        return marketDAO.insertReviewImgs(list);
    }

    @Override
    public int writeQnA(HashMap<String, String> map) {
        return marketDAO.writeQnA(map);
    }

    @Override
    public ArrayList<MarketQnA> getQnAs(PageInfo pi, String productNo) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return marketDAO.getQnAs(productNo, rowBounds);
    }

    @Override
    public ArrayList<MarketReview> getReviews(PageInfo reviewPi, String productNo) {
        int offset = (reviewPi.getCurrentPage() - 1) * reviewPi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, reviewPi.getBoardLimit());
        return marketDAO.getReviews(productNo, rowBounds);
    }

    @Override
    public MarketProduct getProductInfo(String productNo) {
        return marketDAO.getProductInfo(productNo);
    }

    @Override
    public ArrayList<MarketReview> getReviewPointDetail(String productNo) {
        return marketDAO.getReviewPointDetail(productNo);
    }

    @Override
    public int countQnA(String productNo) {
        return marketDAO.countQnA(productNo);
    }

    @Override
    public int countReview(String productNo) {
        return marketDAO.countReview(productNo);
    }

    @Override
    public ArrayList<MarketReview> getReviewList(PageInfo pi, HashMap<String, String> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return marketDAO.getReviewList(map, rowBounds);
    }

    @Override
    public MarketProductAttm getFirstReviewImg(String reviewNo) {
        return marketDAO.getFirstReviewImg(reviewNo);
    }

    @Override
    public ArrayList<MarketProductAttm> getAllImgs(String productNo) {
        return marketDAO.getAllImgs(productNo);
    }

    @Override
    public ArrayList<MarketProductAttm> getRestImgs(String reviewNo) {
        return marketDAO.getRestImgs(reviewNo);
    }

    @Override
    public MarketProductOrder getOrderInfo(HashMap<String, String> map) {
        return marketDAO.getOrderInfo(map);
    }

    @Override
    public int insertReview(MarketReview newReview) {
        return marketDAO.insertReview(newReview);
    }

    @Override
    public int updateProductPoint(HashMap<String, String> map) {
        return marketDAO.updateProductPoint(map);
    }

    @Override
    public MarketQnA getQnA(String questionNo) {
        return marketDAO.getQnA(questionNo);
    }

    @Override
    public int deleteQnA(String questionNo) {
        return marketDAO.deleteQnA(questionNo);
    }

    @Override
    public MarketProductOrder getOrder(String orderNo) {
        return marketDAO.getOrder(orderNo);
    }

    @Override
    public void updateStockSales(HashMap<String, String> map) {
        marketDAO.updateStockSales(map);
    }

    @Override
    public ArrayList<MarketProduct> getIndexProduct(String string) {
        return marketDAO.getIndexProduct(string);
    }

    @Override
    public int getSearchProductCount(String key) {
        return marketDAO.getSearchProductCount(key);
    }

    @Override
    public ArrayList<MarketProduct> getSearchedList(PageInfo pi, HashMap<String, String> map) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return marketDAO.getSearchedList(map, rowBounds);
    }

    @Override
    public ArrayList<MarketProduct> getCartList(ArrayList<String> productNoList) {
        return marketDAO.getCartList(productNoList);
    }

    @Override
    public int insertFisrtCart(MarketProductOrder firstCartOrder) {
        return marketDAO.insertFisrtCart(firstCartOrder);
    }

    @Override
    public int insertRestCart(List<MarketProductOrder> orderList) {
        return marketDAO.insertRestCart(orderList);
    }

    @Override
    public ArrayList<MarketCoupon> getCanDownloadCoupons(String category) {
        return marketDAO.getCanDownloadCoupons(category);
    }

    @Override
    public MarketCoupon getCouponInfo(String couponNo) {
        return marketDAO.getCouponInfo(couponNo);
    }

    @Override
    public int insertCouponToMember(HashMap<String, String> map) {
        return marketDAO.insertCouponToMember(map);
    }

    @Override
    public int getCheckDownload(HashMap<String, String> map) {
        return marketDAO.getCheckDownload(map);
    }

    public int checkReviewed(String orderNo) {
        return marketDAO.checkReviewed(orderNo);
    }

    @Override
    public int checkSelfcheckCouponDownloaded(String memberNo) {
        return marketDAO.checkSelfcheckCouponDownloaded(memberNo);
    }

    @Override
    public int insertSelfcheckCouponToMember(HashMap<String, String> map) {
        return marketDAO.insertSelfcheckCouponToMember(map);
    }

    @Override
    public MarketCoupon getSelfCheckCouponInfo() {
        return marketDAO.getSelfCheckCouponInfo();
    }




}
