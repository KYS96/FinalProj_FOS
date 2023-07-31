package com.kh.slumber.market.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.market.model.vo.MarketCoupon;
import com.kh.slumber.market.model.vo.MarketProduct;
import com.kh.slumber.market.model.vo.MarketProductAttm;
import com.kh.slumber.market.model.vo.MarketProductOrder;
import com.kh.slumber.market.model.vo.MarketQnA;
import com.kh.slumber.market.model.vo.MarketReview;

@Mapper
public interface MarketDAO {

    int getProductCount(HashMap<String, String> categoryMap);

    ArrayList<MarketProduct> getProductList(HashMap<String, String> categoryMap, RowBounds rowBounds);

    MarketProduct getProductDetail(String productNo);

    ArrayList<MarketProductAttm> getProductSubImgs(String productNo);

    ArrayList<MarketProductAttm> getProductDetailImg(String productNo);

    int getReviewCount(String productNo);

    ArrayList<MarketReview> getProductReviews(String productNo);

    ArrayList<MarketProductAttm> getReviewImgs(String productNo);

    int getQnACount(String productNo);

    ArrayList<MarketQnA> getQnAList(String productNo, RowBounds rowBounds);

    ArrayList<MarketCoupon> getUseableCoupons(int memberNo);

    int insertNewOrder(MarketProductOrder newOrder);

    MarketProductOrder getNewOrderInfo(MarketProductOrder newOrder);

    void couponExpiration(String usedCoupon);

    int checkOrder(HashMap<String, String> map);

    MarketProductOrder getRecentOrderInfo(HashMap<String, String> map);

    int writeReview(HashMap<String, String> map);

    int insertReviewImgs(ArrayList<HashMap<String, String>> list);

    int writeQnA(HashMap<String, String> map);

    ArrayList<MarketQnA> getQnAs(String productNo, RowBounds rowBounds);

    ArrayList<MarketReview> getReviews(String productNo, RowBounds rowBounds);

    MarketProduct getProductInfo(String productNo);

    ArrayList<MarketReview> getReviewPointDetail(String productNo);

    int countQnA(String productNo);

    int countReview(String productNo);

    ArrayList<MarketReview> getReviewList(HashMap<String, String> map, RowBounds rowBounds);

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

    ArrayList<MarketProduct> getSearchedList(HashMap<String, String> map, RowBounds rowBounds);

    ArrayList<MarketProduct> getCartList(ArrayList<String> list);

    int insertFisrtCart(MarketProductOrder firstCartOrder);

    int insertRestCart(List<MarketProductOrder> list);

    ArrayList<MarketCoupon> getCanDownloadCoupons(String category);

    MarketCoupon getCouponInfo(String couponNo);

    int insertCouponToMember(HashMap<String, String> map);

    int getCheckDownload(HashMap<String, String> map);

    ArrayList<MarketCoupon> findEnrollCoupon();

    void enrollCoupon(MarketCoupon coupon);

    int checkReviewed(String orderNo);

    int checkSelfcheckCouponDownloaded(String memberNo);

    int insertSelfcheckCouponToMember(HashMap<String, String> map);

    MarketCoupon getSelfCheckCouponInfo();




    
}
