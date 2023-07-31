package com.kh.slumber.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.market.model.service.MarketService;
import com.kh.slumber.market.model.vo.MarketCart;
import com.kh.slumber.market.model.vo.MarketCoupon;
import com.kh.slumber.market.model.vo.MarketProduct;
import com.kh.slumber.market.model.vo.MarketProductAttm;
import com.kh.slumber.market.model.vo.MarketProductOrder;
import com.kh.slumber.market.model.vo.MarketQnA;
import com.kh.slumber.market.model.vo.MarketReview;
import com.kh.slumber.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Controller
public class MarketController {

    @Autowired
    private MarketService marketService;

    @Autowired
    private TemplateEngine templateEngine;

    //상품 목록 페이지로 이동
    @RequestMapping("shop")
    public String hrefnewshop(@RequestParam("category") String category,
                                Model model) {
        model.addAttribute("category", category);
        return "shop";
    }

    //AJAX를 통해 상품 목록 불러오기
    @RequestMapping(value = "getProductList", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getProductList(@RequestParam (value="categoryArr[]",required = false) String[] categoryArr,
                                @RequestParam ("priceRange") String priceRange,
                                @RequestParam ("page") Integer page,
                                @RequestParam ("orderRule") String orderRule){
        
        if(page == null){
            page = 1;
        }
        
        HashMap<String,String> categoryMap = new HashMap<String,String>();
        if(categoryArr == null){
            categoryMap.put("XXX", "XXX");
        }
        if(categoryArr != null){
            for(String category : categoryArr){
                categoryMap.put(category, category);
            }
        }
        categoryMap.put("priceRange", priceRange);
        categoryMap.put("orderRule", orderRule);

        int productCount = marketService.getProductCount(categoryMap);
        PageInfo pi = Pagination.getPageInfo(page, productCount, 12);
        ArrayList<MarketProduct> productList = marketService.getProductList(pi, categoryMap);

        JSONArray jArr = new JSONArray();
        for (MarketProduct p : productList) {
            JSONObject json = new JSONObject();
            json.put("productNo", p.getProductNo());
            json.put("productName", p.getProductName());
            json.put("productPrice", p.getProductPrice());
            json.put("productImage", p.getProductImage());
            json.put("productSales", p.getProductSales());
            json.put("productPoint", p.getProductPoint());
            json.put("eventTitle", p.getEventTitle());
            json.put("eventCode", p.getEventCode());

            jArr.put(json);
        }

        jArr.put(pi);
        return jArr.toString();
    }

    //상품 상세 조회
    @RequestMapping("productDetail")
    public String newdetail(@RequestParam("productNo") String productNo,
                            @RequestParam(value="orderNo", required = false) String orderNo,
                            Model model){
        
        MarketProduct productInfo = marketService.getProductInfo(productNo);
        if(productInfo != null){
            ArrayList<MarketProductAttm> productSubImgs = marketService.getProductSubImgs(productNo);
            ArrayList<MarketProductAttm> productDetailImg = marketService.getProductDetailImg(productNo);

            //쿠폰가져오기
            String category = null;
            if(productInfo.getProductCategoryName().equals("침대")){
                category = "P01";
            }else if(productInfo.getProductCategoryName().equals("베개")){
                category = "P02";
            }else if(productInfo.getProductCategoryName().equals("이불")){
                category = "P03";
            }else if(productInfo.getProductCategoryName().equals("의류")){
                category = "P04";
            }else if(productInfo.getProductCategoryName().equals("건강")){
                category = "P05";
            }else if(productInfo.getProductCategoryName().equals("굿즈")){
                category = "P06";
            }
            ArrayList<MarketCoupon> coupons = marketService.getCanDownloadCoupons(category);
            if(coupons.size() > 0){
                model.addAttribute("coupons", coupons);
            }

            ArrayList<MarketReview> productReviews = marketService.getReviewPointDetail(productNo);
            int oneStar = 0;
            int twoStar = 0;
            int threeStar = 0;
            int fourStar = 0;
            int fiveStar = 0;
            if(productReviews.size()>0){
                for(MarketReview i : productReviews){
                    switch(i.getReviewPoint()){
                        case 1: oneStar += 1; break;
                        case 2: twoStar += 1; break;
                        case 3: threeStar += 1; break;
                        case 4: fourStar += 1; break;
                        case 5: fiveStar += 1; break;
                    }
                }
                double oneStarPercent = Math.round((((double)oneStar / productReviews.size())*100)*10) / 10.0;
                double twoStarPercent = Math.round((((double)twoStar / productReviews.size())*100)*10) / 10.0;
                double threeStarPercent = Math.round((((double)threeStar / productReviews.size())*100)*10) / 10.0;
                double fourStarPercent = Math.round((((double)fourStar / productReviews.size())*100)*10) / 10.0;
                double fiveStarPercent = Math.round((((double)fiveStar / productReviews.size())*100)*10) / 10.0;
    
                model.addAttribute("reviewSize", productReviews.size());
                model.addAttribute("oneStarPercent", oneStarPercent);
                model.addAttribute("twoStarPercent", twoStarPercent);
                model.addAttribute("threeStarPercent", threeStarPercent);
                model.addAttribute("fourStarPercent", fourStarPercent);
                model.addAttribute("fiveStarPercent", fiveStarPercent);
            }else{
                model.addAttribute("reviewSize", 0);
                model.addAttribute("oneStarPercent", 0);
                model.addAttribute("twoStarPercent", 0);
                model.addAttribute("threeStarPercent", 0);
                model.addAttribute("fourStarPercent", 0);
                model.addAttribute("fiveStarPercent", 0);
            }

            model.addAttribute("productInfo", productInfo);
            model.addAttribute("productSubImgs", productSubImgs);
            model.addAttribute("productDetailImg", productDetailImg);
            model.addAttribute("reviewCount", productReviews.size());

            if(orderNo != null){
                model.addAttribute("orderNo", orderNo);
            }
            return "productDetail";
        }else{
            return "404";
        }
    }

    //AJAX를 통해 상품 상세보기 안 상품 리뷰 목록 불러오기
    @GetMapping("getReviewList")
    @ResponseBody
    public ResponseEntity<String> getReviewList(@RequestParam ("productNo") String productNo,
                                                @RequestParam ("page") Integer page,
                                                @RequestParam ("orderRule") String orderRule){
        if(page == null){
            page = 1;
        }
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("productNo", productNo);
        map.put("orderRule", orderRule);
        
        int reviewCount = marketService.countReview(productNo);
        PageInfo pi = Pagination.getPageInfo(page, reviewCount, 5);
        ArrayList<MarketReview> reviewList = marketService.getReviewList(pi, map);

        if(!reviewList.isEmpty()){
            for(MarketReview r : reviewList){
                String firstImg = "";
                MarketProductAttm checkFirstImg = marketService.getFirstReviewImg(r.getReviewNo());
                if(checkFirstImg != null){
                    firstImg = checkFirstImg.getProductAttmURL();
                }
                r.setFirstImg(firstImg);
            }
            
            Context context = new Context();
            context.setVariable("reviewList", reviewList);
            context.setVariable("pi", pi);
            context.setVariable("reviewCount", reviewCount);
            
            String html = templateEngine.process("th-review", context);
            return ResponseEntity.ok(html);
        }else{
            System.out.println("리뷰 리스트가 비어있음");
            return null;
        }
    }

    //AJAX를 통해 상품의 전체 리뷰 이미지 불러오기
    @GetMapping(value="getAllImgs", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAllImgs(@RequestParam ("productNo") String productNo){
        ArrayList<MarketProductAttm> allImgs = marketService.getAllImgs(productNo);
        JSONArray jArr = new JSONArray();
        for(MarketProductAttm i : allImgs){
            JSONObject json = new JSONObject();
            json.put("productAttmNo", i.getProductAttmNo());
            json.put("productAttmURL", i.getProductAttmURL());
            jArr.put(json);
        }
        return jArr.toString();
    }

    //AJAX를 통해 개별 리뷰의 전체 이미지 불러오기
    @GetMapping(value="getRestImgs", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getRestImgs(@RequestParam ("reviewNo") String reviewNo){
        ArrayList<MarketProductAttm> restImgs = marketService.getRestImgs(reviewNo);
        JSONArray jArr = new JSONArray();
        for(MarketProductAttm i : restImgs){
            JSONObject json = new JSONObject();
            json.put("productAttmURL", i.getProductAttmURL());
            jArr.put(json);
        }
        return jArr.toString();
    }

    //AJAX를 통해 상품 상세보기 안 QnA 목록 불러오기
    @RequestMapping(value="getQnaList", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getQnaList(@RequestParam ("productNo") String productNo,
                            @RequestParam ("page") Integer page){
        if(page == null){
            page = 1;
        }
        int qnaCount = marketService.countQnA(productNo);
        PageInfo pi = Pagination.getPageInfo(page, qnaCount, 5);
        ArrayList<MarketQnA> qnaList = marketService.getQnAList(pi, productNo);

        JSONArray jArr = new JSONArray();
        if(!qnaList.isEmpty()){
            for(MarketQnA q : qnaList){
                JSONObject json = new JSONObject();
                json.put("questionNo", q.getQuestionNo());
                json.put("memberNo", q.getMemberNo());
                json.put("memberNickName", q.getMemberNickName());
                json.put("questionTitle", q.getQuestionTitle());
                json.put("questionUpdateDate", q.getQuestionUpdateDate());
                json.put("questionContent", q.getQuestionContent());
                json.put("questionAnswer", q.getQuestionAnswer());
                json.put("PRODUCT_NO", q.getProductNo());
                jArr.put(json);
            }
            jArr.put(qnaCount);
            jArr.put(pi);
            return jArr.toString();
        }else{
            System.out.println("QnA리스트가 비어있음");
            return jArr.toString();
        }
    }

    //결제 요청 페이지로 이동
    @RequestMapping("payment")
    public String paymentPage(
            @RequestParam(value = "productNo", required = false) int productNo,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "productImage", required = false) String productImage,
            @RequestParam(value = "eventTitle", required = false) String eventTitle,
            @RequestParam(value = "eventCode", required = false) Integer eventCode,
            @RequestParam(value = "purchaseQuantity", required = false) int purchaseQuantity,
            @RequestParam(value = "totalPrice", required = false) int totalPrice,
            @RequestParam(value = "originalPrice", required = false) int originalPrice,
            HttpSession session, Model model) {
        
        model.addAttribute("productNo", productNo);
        model.addAttribute("productName", productName);
        model.addAttribute("productImage", productImage);
        model.addAttribute("eventTitle", eventTitle);
        model.addAttribute("eventCode", eventCode);
        model.addAttribute("purchaseQuantity", purchaseQuantity);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("discount", originalPrice * purchaseQuantity - totalPrice);
        
        return "payment";

    }

    //결제 결과 페이지로 이동(1)
    @PostMapping("paying")
    public String paying(@RequestParam("productNo") String productNo,
                            @RequestParam("productName") String productName,
                            @RequestParam("productImage") String productImage,
                            @RequestParam(value="eventTitle", required=false) String eventTitle,
                            @RequestParam("purchaseQuantity") String purchaseQuantity,
                            @RequestParam("finalPrice") String finalPrice,
                            @RequestParam("memo") String memo,
                            @RequestParam("mainAddr") String mainAddr,
                            @RequestParam("detailAddr") String detailAddr,
                            @RequestParam("orderType") String orderType,
                            @RequestParam(value="usedCoupon", required=false) String usedCoupon,
                            HttpSession session, RedirectAttributes ra){
        
        while(finalPrice.contains(",")){
            finalPrice = finalPrice.replace(",", "");
        }

        Member m = (Member) session.getAttribute("loginUser");
        String memberNo = m.getMemberNo();

        MarketProductOrder newOrder = new MarketProductOrder();
        newOrder.setMemberNo(memberNo);
        newOrder.setProductNo(productNo);
        newOrder.setProductName(productName);
        newOrder.setProductImage(productImage);
        newOrder.setOrderAmount(purchaseQuantity);
        newOrder.setOrderPrice(finalPrice);
        newOrder.setOrderMemo(memo);
        newOrder.setOrderAddr(mainAddr);
        newOrder.setOrderDetailAddr(detailAddr);
        newOrder.setOrderType(orderType);

        if (usedCoupon != null) {
            newOrder.setCouponIssuanceNo(usedCoupon);
            marketService.couponExpiration(usedCoupon);
        }

        int result = marketService.insertNewOrder(newOrder);

        if(result>0){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("productNo", productNo);
            map.put("purchaseQuantity", purchaseQuantity);

            marketService.updateStockSales(map);
            
            ra.addAttribute("orderNo", newOrder.getOrderNo());
            ra.addAttribute("productName", productName);
            ra.addAttribute("productImage", productImage);

            return "redirect:orderSuccess";
        }else{
            System.out.println("구매실패");
            return null;
        }
    }

    //결제 결과 페이지로 이동(2)
    @GetMapping("orderSuccess")
    public String orderSuccess(@RequestParam("orderNo") String orderNo, 
                                @RequestParam("productName") String productName,
                                @RequestParam("productImage") String productImage,
                                Model model){

        MarketProductOrder orderInfo = marketService.getOrder(orderNo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("productName", productName);
        model.addAttribute("productImage", productImage);

        return "paymentSuccess";
    }

    //회원이 보유한 쿠폰 정보 가져오기
    @RequestMapping("couponPopup")
    public String couponPopup(HttpSession session, Model model){

        Member m = (Member)session.getAttribute("loginUser");
        int memberNo = Integer.parseInt(m.getMemberNo());

        ArrayList<MarketCoupon> coupons = marketService.getUseableCoupons(memberNo);

        model.addAttribute("coupons", coupons);
        return "couponPopup";
    }

    //QnA작성 팝업 띄우기
    @RequestMapping("QnAPopup")
    public String QnAPopup(@RequestParam("productNo") String productNo, Model model) {
        model.addAttribute("productNo", productNo);
        return "QnAPopup";
    }

    //리뷰작성 팝업 띄우기
    @RequestMapping("reviewPopup")
    public String reviewPopup(@RequestParam("orderNo") String orderNo,
                                HttpSession session, Model model){
        int checkReviewed = marketService.checkReviewed(orderNo);
        if(checkReviewed <= 0){
            Member m = ((Member)session.getAttribute("loginUser"));
    
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("memberNo", m.getMemberNo());
            map.put("orderNo", orderNo);
            int checkOrder = marketService.checkOrder(map);
    
            if(checkOrder == 1){
                MarketProductOrder orderInfo = marketService.getOrderInfo(map);
                model.addAttribute("orderInfo", orderInfo);
                return "reviewPopup";
            }else{
                return null;
            }
        }else{
            return "redirect:/alreadyReviewd";
        }
    }

    //QnA작성
    @PostMapping("writeQnA")
    public String writeQnA(Model model, HttpSession session,
            @RequestParam("productNo") String productNo,
            @RequestParam("QnATitle") String QnATitle,
            @RequestParam("QnAContent") String QnAContent) {

        Member m = ((Member) session.getAttribute("loginUser"));

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("QnATitle", QnATitle);
        map.put("QnAContent", QnAContent);
        map.put("memberNo", m.getMemberNo());

        int result = marketService.writeQnA(map);
        if(result>0){
            return "redirect:insertQnASuccess";
        }else{
            return null;
        }
    }

    //QnA작성 성공 key반환
    @GetMapping("insertQnASuccess")
    public String insertQnASuccess(Model model){
        model.addAttribute("key", "key");
        return "QnAPopup";
    }

    //리뷰및 리뷰이미지 등록
    @PostMapping("insertReview")
    public String insertReview(@RequestParam("point") String point,
                                @RequestParam("orderNo") String orderNo,
                                @RequestParam("content") String content,
                                @RequestParam("productNo") String productNo,
                                @RequestParam(value="reviewImgUrl",required = false) String[] reviewImgUrls,
                                Model model){
        ///////
        MarketReview newReview = new MarketReview();
        newReview.setOrderNo(orderNo);
        newReview.setReviewContent(content);
        newReview.setReviewPoint(Integer.parseInt(point));

        int result = marketService.insertReview(newReview);
        if(result > 0){
            updateProductPoint(productNo, point);
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            if(reviewImgUrls != null){
                for(int i = 0; i < reviewImgUrls.length; i++){
                    HashMap<String, String> attmMap = new HashMap<String, String>();
                    String originalName = reviewImgUrls[i].substring(reviewImgUrls[i].lastIndexOf("/") + 38);
                    String reName = reviewImgUrls[i].substring(reviewImgUrls[i].lastIndexOf("/") + 1);

                    attmMap.put("productNo", productNo);
                    attmMap.put("attmUrl", reviewImgUrls[i]);
                    attmMap.put("attmName", originalName);
                    attmMap.put("attmSaveName", reName);
                    attmMap.put("attmCategory", "s_review_"+newReview.getReviewNo());
                    list.add(attmMap);
                }
                int insertReviewAttmResult = marketService.insertReviewImgs(list);
                if(insertReviewAttmResult <= 0){
                    System.out.println("이미지등록실패");
                }
            }
            return "redirect:/insertReviewSuccess";
        }else{
            return null;
        }
        ///////
    }

    //리뷰등록 성공
    @GetMapping("/insertReviewSuccess")
    public String insertReviewSuccess(Model model){
        model.addAttribute("key", "key");
        return "reviewPopup";
    }

    //이미 리뷰를 작성한적 있음
    @GetMapping("/alreadyReviewd")
    public String alreayReviewd(Model model){
        model.addAttribute("alreayReviewd", "alreayReviewd");
        model.addAttribute("key", "key");
        return "reviewPopup";
    }

    //상품 평점 업데이트
    public void updateProductPoint(String productNo, String point){

        int reviewCount = marketService.countReview(productNo); //총 리뷰 개수

        MarketProduct product = marketService.getProductInfo(productNo);

        double newPoint = (product.getProductPoint() * (reviewCount-1) + Integer.parseInt(point)) / (reviewCount);

        newPoint = Math.round(newPoint * 10.0) / 10.0;

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("productNo", productNo);
        map.put("newPoint", newPoint+"");

        int result = marketService.updateProductPoint(map);
        if(result != 1){
            System.out.println("상품 평점 업데이트 실패");
        }
    }

    //QnA내용 조회
    @RequestMapping("QnAContent")
    public String QnAContentView(@RequestParam("questionNo") String questionNo,
                                Model model){
        MarketQnA qna = marketService.getQnA(questionNo);
        model.addAttribute("qna", qna);
        return "qnaContentPopup";
    }

    //작성한 QnA 삭제
    @PostMapping("deleteQnA")
    public String deleteQnA(@RequestParam("questionNo") String questionNo){
        int result = marketService.deleteQnA(questionNo);
        if(result > 0){
            return "redirect:deleteQnASuccess";
        }else{
            return null;
        }
    }

    //작성한 QnA 삭제 성공
    @GetMapping("deleteQnASuccess")
    public String deleteQnASuccess(Model model){
        model.addAttribute("key", "key");
        return "qnaContentPopup";
    }

    //AJAX를 통해 장바구니 목록을 호출해 화면에 전달
    @GetMapping("getCartList")
    @ResponseBody
    public ResponseEntity<String> getCartList(@RequestParam("cartArr") String cartArrJSON){
            JSONArray cartArr = new JSONArray(cartArrJSON);
            ArrayList<String> productNoList = new ArrayList<>();
            ArrayList<MarketCart> cartList = new ArrayList<>();
            for (int i = 0; i < cartArr.length(); i++) {
                JSONObject cartItem = cartArr.getJSONObject(i);
                String productNo = cartItem.getString("productNo");
                String amount = cartItem.getString("amount");

                productNoList.add(productNo);
                MarketCart cart = new MarketCart();
                cart.setProductNo(productNo);
                cart.setAmount(amount);
                cartList.add(cart);
            }
            ArrayList<MarketProduct> productList = marketService.getCartList(productNoList);
            ArrayList<MarketCart> cartProductList = new ArrayList<>();
            for(MarketProduct p : productList){
                for(MarketCart c : cartList){
                    if(c.getProductNo().equals(p.getProductNo())){
                        c.setProductName(p.getProductName());
                        c.setProductPrice(p.getProductPrice());
                        c.setEventTitle(p.getEventTitle());
                        c.setEventCode(p.getEventCode());
                        c.setProductImage(p.getProductImage());

                        cartProductList.add(c);
                    }
                }
            }
            Context context = new Context();
            context.setVariable("cartProductList", cartProductList);
            String html = templateEngine.process("th-cart", context);
            return ResponseEntity.ok(html);
    }

    @GetMapping("updateSummary")
    @ResponseBody
    public ResponseEntity<String> updateSummary(@RequestParam("data") String dataJSON){
        JSONArray dataList = new JSONArray(dataJSON);
        ArrayList<MarketCart> summaryList = new ArrayList<>();
        for(int i = 0; i < dataList.length(); i++){
            JSONObject dataItem = dataList.getJSONObject(i);
            String productName = dataItem.getString("productName");
            String productPrice = dataItem.getString("productPrice");
            String amount = dataItem.getString("amount");
            
            productPrice = productPrice.substring(0, productPrice.length() - 1);
            while(productPrice.contains(",")){
                productPrice = productPrice.replace(",", "");
            }
            
            MarketCart c = new MarketCart();
            c.setProductName(productName);
            c.setProductPrice(Integer.parseInt(productPrice));
            c.setAmount(amount);

            summaryList.add(c);
        }
        Context context = new Context();
        context.setVariable("summaryList", summaryList);
        String html = templateEngine.process("th-cart-summary", context);
        return ResponseEntity.ok(html);
    }

    @RequestMapping("multiPayment")
    public String multiPayment(@RequestParam("cartData") String cartData, Model model){
        JSONArray dataList = new JSONArray(cartData);
        ArrayList<MarketCart> cartList = new ArrayList<>();
        for(int i = 0; i < dataList.length(); i++){
            JSONObject dataItem = dataList.getJSONObject(i);
            String productNo = dataItem.getString("productNo");
            String productName = dataItem.getString("productName");
            String amount = dataItem.getString("amount");
            String total = dataItem.getString("total");
            String productImage = dataItem.getString("productImage");

            total = total.substring(0, total.length() - 1);
            while(total.contains(",")){
                total = total.replace(",", "");
            }

            MarketCart c= new MarketCart();
            c.setProductNo(productNo);
            c.setProductName(productName);
            c.setAmount(amount);
            c.setTotal(total);
            c.setProductImage(productImage);

            cartList.add(c);
        }
        model.addAttribute("cartList", cartList);
        return "multiPayment";
    }

    @PostMapping("multiPaying")
    public String multiPaying(@RequestParam("cartData") String cartData,
                                @RequestParam("orderType") String orderType,
                                @RequestParam("mainAddr") String mainAddr,
                                @RequestParam("detailAddr") String detailAddr,
                                @RequestParam(value="usedCoupon", required=false) String usedCoupon,
                                @RequestParam("memo") String memo,
                                @RequestParam("cartName") String cartName,
                                @RequestParam("image") String image,
                                @RequestParam(value="discount",required = false) Integer discount,
                                HttpSession session, RedirectAttributes ra){
        if(discount == null){
            discount = 0;
        }
        Member m = (Member) session.getAttribute("loginUser");
        JSONArray dataList = new JSONArray(cartData);
        int totalAll = 0;
        List<MarketProductOrder> orderList = new ArrayList<>();
        for(int i = 0; i < dataList.length(); i++){
            JSONObject dataItem = dataList.getJSONObject(i);
            String productNo = dataItem.getString("productNo");
            String amount = dataItem.getString("amount");
            String total = dataItem.getString("total");
            totalAll += Integer.parseInt(total);
            
            MarketProductOrder o = new MarketProductOrder();
            o.setMemberNo(m.getMemberNo());
            o.setProductNo(productNo);
            o.setOrderAmount(amount);
            o.setOrderType(orderType);
            o.setOrderAddr(mainAddr);
            o.setOrderDetailAddr(detailAddr);
            if(usedCoupon != null){
                o.setCouponIssuanceNo(usedCoupon);
                marketService.couponExpiration(usedCoupon);
            }
            o.setOrderPrice(total);
            o.setOrderMemo(memo);
            orderList.add(o);
        }
        int result1 = marketService.insertFisrtCart(orderList.get(0));
        int result2 = 0;
        String cartNo = null;
        cartNo = orderList.get(0).getOrderNo();
        if(orderList.size() > 1){
            orderList.remove(0);
    
            for(MarketProductOrder order : orderList){
                order.setCartNo(cartNo);
            }
            result2 = marketService.insertRestCart(orderList);
        }
        if(result1 + result2 > 0){
            ra.addAttribute("cartName", cartName + "외 " + orderList.size());
            ra.addAttribute("totalAll", totalAll);
            ra.addAttribute("memo", memo);
            ra.addAttribute("image", image);
            ra.addAttribute("cartNo", cartNo);
            ra.addAttribute("discount", discount);
            // ra.addAttribute("usedCoupon", usedCoupon);
            return "redirect:multiOrderSuccess";
        }else{
            return null;
        }
    }

    @GetMapping("multiOrderSuccess")
    public String multiOrderSuccess(@RequestParam("cartName") String cartName,
                                    @RequestParam("totalAll") int totalAll,
                                    @RequestParam("memo") String memo,
                                    @RequestParam("image") String image,
                                    @RequestParam(value="cartNo", required = false) String cartNo,
                                    @RequestParam(value="discount", required = false) Integer discount,
                                    // @RequestParam(value="usedCoupon", required = false) String usedCoupon,
                                    Model model){
        if(cartNo != null){
            MarketProductOrder order = marketService.getOrder(cartNo);
            model.addAttribute("order", order);
        }
        if(discount > 0 || discount != null){
            totalAll = totalAll - discount;
        }
        System.out.println("*******");
        System.out.println(discount);
        System.out.println(totalAll);
        System.out.println("*******");
        if(totalAll < 50000){
            totalAll = totalAll + 3000;
        }
        model.addAttribute("cartName", cartName);
        model.addAttribute("totalAll", totalAll);
        model.addAttribute("memo", memo);
        model.addAttribute("image", image);
        model.addAttribute("cartNo", cartNo);

        return "multiPaymentSuccess";
    }

    @RequestMapping("myCart")
    public String hrefCart(){
        return "cart";
    }

    @RequestMapping("searchProduct")
    @ResponseBody
    public String searchProduct(@RequestParam("key") String key,
                                @RequestParam("orderRule") String orderRule,
                                @RequestParam("searchPage") Integer searchPage){
        if(searchPage == null){
            searchPage = 1;
        }

        int searchProductCount = marketService.getSearchProductCount(key);
        PageInfo pi = Pagination.getPageInfo(searchPage, searchProductCount, 12);
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("key", key);
        map.put("orderRule", orderRule);
        ArrayList<MarketProduct> searchedList = marketService.getSearchedList(pi, map);

        JSONArray jArr = new JSONArray();
        for (MarketProduct p : searchedList) {
            JSONObject json = new JSONObject();
            json.put("productNo", p.getProductNo());
            json.put("productName", p.getProductName());
            json.put("productPrice", p.getProductPrice());
            json.put("productImage", p.getProductImage());
            json.put("productSales", p.getProductSales());
            json.put("productPoint", p.getProductPoint());
            json.put("eventTitle", p.getEventTitle());
            json.put("eventCode", p.getEventCode());

            jArr.put(json);
        }
        jArr.put(pi);
        return jArr.toString();
    }
    
    @GetMapping("downloadCoupon")
    @ResponseBody
    public String downloadCoupon(@RequestParam("couponNo") String couponNo,
                                HttpSession session){

        Member m = (Member)session.getAttribute("loginUser");
        MarketCoupon couponInfo = marketService.getCouponInfo(couponNo);
        HashMap<String,String> map = new HashMap<>();
        map.put("memberNo", m.getMemberNo());
        map.put("couponNo", couponNo);
        map.put("couponStartDate", couponInfo.getCouponStartDate().substring(0, 10));
        map.put("couponEndDate", couponInfo.getCouponEndDate().substring(0, 10));

        int checkResult = marketService.getCheckDownload(map);
        if(checkResult > 0){
            return "fail";
        }else{
            int result = marketService.insertCouponToMember(map);
            return result == 1 ? "success" : "fail";
        }
        
    }

    @GetMapping("downloadSelfcheckCoupon")
    @ResponseBody
    public String downloadSelfcheckCoupon(HttpSession session){
        Member m = (Member)session.getAttribute("loginUser");
        String memberNo = m.getMemberNo();
        int checkResult = marketService.checkSelfcheckCouponDownloaded(memberNo);
        if(checkResult > 0){
            return "alreadyDownload";
        }else{
            MarketCoupon selfcheckCoupon = marketService.getSelfCheckCouponInfo();
            System.out.println(selfcheckCoupon);
            if(selfcheckCoupon == null){
                return "noCoupon";
            }else{
                HashMap<String,String> map = new HashMap<>();
                map.put("memberNo", memberNo);
                map.put("couponNo", selfcheckCoupon.getCouponNo());
                int result = marketService.insertSelfcheckCouponToMember(map);
                System.out.println(result);
                return result == 1 ? "success" : "fail";
            }
        }
    }



}
