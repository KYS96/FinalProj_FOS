package com.kh.slumber.market.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.market.model.vo.MarketCoupon;
import com.kh.slumber.market.model.vo.MarketProduct;
import com.kh.slumber.market.model.vo.MarketProductAttm;
import com.kh.slumber.market.model.vo.MarketProductOrder;
import com.kh.slumber.market.model.vo.MarketQnA;
import com.kh.slumber.market.model.vo.MarketReview;

public interface MarketService {

    int getProductCount(HashMap<String, String> categoryMap);

    ArrayList<MarketProduct> getProductList(PageInfo pi, HashMap<String, String> categoryMap);

    MarketProduct getProductDetail(String productNo);

    ArrayList<MarketProductAttm> getProductSubImgs(String productNo);

    ArrayList<MarketProductAttm> getProductDetailImg(String productNo);

    int getReviewCount(String productNo);

    ArrayList<MarketReview> getProductReviews(String productNo);

    ArrayList<MarketProductAttm> getReviewImgs(String productNo);

    int getQnACount(String productNo);

    ArrayList<MarketQnA> getQnAList(PageInfo pi, String productNo);

    ArrayList<MarketCoupon> getUseableCoupons(int memberNo);

    int insertNewOrder(MarketProductOrder newOrder);

    MarketProductOrder getNewOrderInfo(MarketProductOrder newOrder);

    void couponExpiration(String usedCoupon);

    int checkOrder(HashMap<String, String> map);

    MarketProductOrder getRecentOrderInfo(HashMap<String, String> map);

    int writeReview(HashMap<String, String> map);

    int insertReviewImgs(ArrayList<HashMap<String, String>> list);

    int writeQnA(HashMap<String, String> map);

    ArrayList<MarketQnA> getQnAs(PageInfo pi, String productNo);

    ArrayList<MarketReview> getReviews(PageInfo reviewPi, String productNo);

    MarketProduct getProductInfo(String productNo);

    ArrayList<MarketReview> getReviewPointDetail(String productNo);

    int countQnA(String productNo);

    int countReview(String productNo);

    ArrayList<MarketReview> getReviewList(PageInfo pi, HashMap<String, String> map);

    MarketProductAttm getFirstReviewImg(String reviewNo);

    ArrayList<MarketProductAttm> getAllImgs(String productNo);

    ArrayList<MarketProductAttm> getRestImgs(String reviewNo);

    MarketProductOrder getOrderInfo(HashMap<String, String> map);

    int insertReview(MarketReview newReview);

    int updateProductPoint(HashMap<String, String> map);

    MarketQnA getQnA(String questionNo);

    int deleteQnA(String questionNo);

    MarketProductOrder getOrder(String orderNo);

    void updateStockSales(HashMap<String, String> map);

    ArrayList<MarketProduct> getIndexProduct(String string);

    int getSearchProductCount(String key);

    ArrayList<MarketProduct> getSearchedList(PageInfo pi, HashMap<String, String> map);

    ArrayList<MarketProduct> getCartList(ArrayList<String> productNoList);

    int insertFisrtCart(MarketProductOrder marketProductOrder);

    int insertRestCart(List<MarketProductOrder> orderList);

    ArrayList<MarketCoupon> getCanDownloadCoupons(String category);

    MarketCoupon getCouponInfo(String couponNo);

    int insertCouponToMember(HashMap<String, String> map);

    int getCheckDownload(HashMap<String, String> map);

    int checkReviewed(String orderNo);

    int checkSelfcheckCouponDownloaded(String memberNo);

    int insertSelfcheckCouponToMember(HashMap<String, String> map);

    MarketCoupon getSelfCheckCouponInfo();





    
    
}

