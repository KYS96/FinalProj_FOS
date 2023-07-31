package com.kh.slumber.admin.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.slumber.admin.model.exception.AdminException;
import com.kh.slumber.admin.model.service.AdminService;
import com.kh.slumber.admin.model.vo.AdminBoard;
import com.kh.slumber.admin.model.vo.AdminCoupon;
import com.kh.slumber.admin.model.vo.AdminCouponIssuance;
import com.kh.slumber.admin.model.vo.AdminEvent;
import com.kh.slumber.admin.model.vo.AdminProduct;
import com.kh.slumber.admin.model.vo.AdminProductImages;
import com.kh.slumber.admin.model.vo.AdminProductOrder;
import com.kh.slumber.admin.model.vo.AdminProductSearchCondition;
import com.kh.slumber.admin.model.vo.AdminQuestionBoard;
import com.kh.slumber.admin.model.vo.AdminReply;
import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AdminService aService;

    @GetMapping("/productUpdate.admin")
    public String productUpdate(Model model) {
        AdminProductSearchCondition sc = new AdminProductSearchCondition();
        model.addAttribute("sc", sc);
        return "productUpdateAdmin";
    }

    @GetMapping("/productEnroll.admin")
    public String productEnrollAdmin() {
        return "productEnrollAdmin";
    }

    @GetMapping("/productQuestion.admin")
    public String productQuestionAdmin() {
        return "productQuestionAdmin";
    }

    @GetMapping("/memberManage.admin")

    public String memberManageAdmin() {
        return "memberManageAdmin";
    }

    @GetMapping("/deliveryManage.admin")
    public String deliveryManageAdmin() {
        return "deliveryManageAdmin";
    }

    @GetMapping("/delivery.admin")
    public String deliveryAdmin() {
        return "deliveryAdmin";

    }

    @GetMapping("/couponManage.admin")
    public String couponManageAdmin() {
        return "couponManageAdmin";
    }

    @GetMapping("/couponMaking.admin")
    public String couponMakingAdmin(Model model) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        AdminCoupon ac = new AdminCoupon();
        ac.setCouponOpenDate(formattedDate);
        ac.setCouponCloseDate(formattedDate);
        model.addAttribute("ac", ac);
        return "couponMakingAdmin";
    }

    @GetMapping("/couponDownload.admin")
    public String couponDownloadAdmin(Model model, HttpSession session) {
        ArrayList<AdminCoupon> list = aService.getCouponList();
        model.addAttribute("list", list);
        return "couponDownloadAdmin";
    }

    @GetMapping("/boardWrite.admin")
    public String boardWriteAdmin() {
        return "boardWriteAdmin";
    }

    @GetMapping("/communityManage.admin")
    public String communityManageAdmin() {

        return "communityManageAdmin";
    }

    @GetMapping("/boardManage.admin")
    public String boardManageAdmin() {
        return "boardManageAdmin";
    }

    @GetMapping("/eventManage.admin")
    public String eventManageAdmin() {
        return "eventManageAdmin";
    }

    @GetMapping("/eventMaking.admin")
    public String eventMakingAdmin(Model model) {
        return "eventMakingAdmin";
    }

    @PostMapping("/enrollProduct.admin")
    public String enrollProduct(@ModelAttribute AdminProduct p, Model model,
            @RequestParam(value = "subImagesUrl", required = false) String[] subImages) {

        int result1 = 0;
        result1 = aService.enrollProduct(p);
        if (result1 <= 0) {
            throw new AdminException("상품 등록에 실패했습니다.");
        }

        p.setProductNo(aService.selectProductNo(p.getProductImage()));

        int result2 = 0;
        ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
        if (subImages != null && subImages.length > 0) {
            for (int i = 0; i < subImages.length; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                String originalName = subImages[i].substring(subImages[i].lastIndexOf("/") + 38);
                String reName = subImages[i].substring(subImages[i].lastIndexOf("/") + 1);
                map.put("productNo", p.getProductNo());
                map.put("attm_url", subImages[i]);
                map.put("attm_name", originalName);
                map.put("attm_save_name", reName);
                map.put("attm_category", "s_sub");
                list1.add(map);
            }
            result2 = aService.enrollProductImages(list1);

            if (result2 <= 0) {
                throw new AdminException("서브 이미지 등록에 실패했습니다.");
            }

        }
        model.addAttribute("msg", "success");
        return "productEnrollAdmin";
    }

    @GetMapping("/searchProduct.admin")
    public String searchProduct(Model model, @ModelAttribute AdminProductSearchCondition sc,
            @RequestParam(value = "page", required = false) Integer page) {

        int currentPage = 1;
        if (page != null) {
            currentPage = page;
        }

        if (sc.getQuery() == null) {
            sc.setQuery("");
        }

        ArrayList<String> productCategoryNoList = sc.getProductCategoryNoList();

        if (productCategoryNoList.contains("all")) {
            productCategoryNoList.remove("all");
            productCategoryNoList.add("P01");
            productCategoryNoList.add("P02");
            productCategoryNoList.add("P03");
            productCategoryNoList.add("P04");
            productCategoryNoList.add("P05");
            productCategoryNoList.add("P06");
        }
        sc.setProductCategoryNoList(productCategoryNoList);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sc", sc);

        int listCount = aService.getSearchListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminProduct> list = new ArrayList<AdminProduct>();
        list = aService.searchProductAdmin(pi, map);

        if (list != null) {
            for (AdminProduct a : list) {
                if (a.getProductResistDate() != null) {
                    a.setProductResistDate(a.getProductResistDate().split(" ")[0]);
                }
                if (a.getProductUpdate() != null) {
                    a.setProductUpdate(a.getProductUpdate().split(" ")[0]);
                }
            }

            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("pi", pi);
            model.addAttribute("sc", sc);

        }

        return "productUpdateAdmin";
    }

    @GetMapping("/changeProductStatus.admin")
    @ResponseBody
    public String changeProductStatus(@RequestParam(value = "productNo", required = false) String productNo,
            @RequestParam(value = "productStatus", required = false) String productStatus) {

        int result = 0;
        if (productStatus.equals("진열 중")) {
            result = aService.changeProductStatusToN(productNo);
        } else if (productStatus.equals("미진열")) {
            result = aService.changeProductStatusToY(productNo);
        }
        JSONObject json = new JSONObject();
        if (result > 0) {
            if (productStatus.equals("진열 중")) {
                json.put("changedProductStatus", "미진열");
            } else if (productStatus.equals("미진열")) {
                json.put("changedProductStatus", "진열 중");
            }
        }else{
            json.put("error", "error");
        }

        return json.toString();
    }

    @GetMapping("/changeProductCategory.admin")
    @ResponseBody
    public String changeProductCategory(@RequestParam(value = "productNo", required = false) String productNo,
            @RequestParam(value = "selectedProductCategory", required = false) String selectedProductCategory) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("selectedProductCategory", selectedProductCategory);

        int result = 0;
        result = aService.changeProductCategory(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedProductCategory", selectedProductCategory);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeProductName.admin")
    @ResponseBody
    public String changeProductName(@RequestParam(value = "productNo", required = false) String productNo,
            @RequestParam(value = "selectedProductName", required = false) String selectedProductName) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("selectedProductName", selectedProductName);

        int result = 0;
        result = aService.changeProductName(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedProductName", selectedProductName);
        } else {
            json.put("error", "error");
        }
        return json.toString();

    }

    @GetMapping("/changeProductPrice.admin")
    @ResponseBody
    public String changeProductPrice(@RequestParam("productNo") String productNo,
            @RequestParam("selectedProductPrice") String selectedProductPrice) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("selectedProductPrice", selectedProductPrice);

        int result = 0;
        result = aService.changeProductPrice(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedProductPrice", selectedProductPrice);
        } else {
            json.put("error", "error");
        }
        return json.toString();

    }

    @GetMapping("/changeProductStock.admin")
    @ResponseBody
    public String changeProductStock(@RequestParam(value = "productNo", required = false) String productNo,
            @RequestParam(value = "selectedProductStock", required = false) String selectedProductStock) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productNo", productNo);
        map.put("selectedProductStock", selectedProductStock);

        int result = 0;
        result = aService.changeProductStock(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedProductStock", selectedProductStock);
        } else {
            json.put("error", "error");
        }
        return json.toString();

    }

    @PostMapping("/productUpdateDetail.admin")
    public String moveProductUpdateDetail(Model model, @ModelAttribute AdminProductSearchCondition sc,
            @RequestParam("page") Integer page) {

        String productNo = sc.getSelectProductNo();
        AdminProduct p = aService.searchProductNo(productNo);
        AdminProductImages frontImage = aService.searchFrontImage(productNo);

        HashMap<String, String> subMap = new HashMap<String, String>();
        subMap.put("attm_category", "s_sub");
        subMap.put("productNo", productNo);
        ArrayList<AdminProductImages> subImages = new ArrayList<AdminProductImages>();
        subImages = aService.searchImages(subMap);

        HashMap<String, String> detailMap = new HashMap<String, String>();
        detailMap.put("attm_category", "s_detail");
        detailMap.put("productNo", productNo);
        ArrayList<AdminProductImages> detailImages = new ArrayList<AdminProductImages>();
        detailImages = aService.searchImages(detailMap);

        if (p != null) {
            model.addAttribute("page", page);
            model.addAttribute("sc", sc);
            model.addAttribute("p", p);
            model.addAttribute("frontImage", frontImage);
            model.addAttribute("subImages", subImages);
            model.addAttribute("detailImages", detailImages);
            return "productUpdateDetailAdmin";
        } else {
            throw new AdminException("상품 정보를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/updateProduct.admin")
    public String updateProduct(@ModelAttribute AdminProduct p, Model model,
                                @ModelAttribute AdminProductSearchCondition sc,
                                @RequestParam("page") Integer page,
                                @RequestParam(value = "subImagesUrl", required = false) String[] subImages) {

        int result1 = 0;
        result1 = aService.updateProduct(p);

        if (result1 <= 0) {
            throw new AdminException("상품 등록에 실패했습니다.");
        }

        int result2 = 0;
        ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();

        if (subImages != null && subImages.length > 0) {
            for (int i = 0; i < subImages.length; i++) {
                if (subImages[i] != "" && subImages[i] != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    String originalName = subImages[i].substring(subImages[i].lastIndexOf("/") + 38);
                    String reName = subImages[i].substring(subImages[i].lastIndexOf("/") + 1);
                    map.put("productNo", p.getProductNo());
                    map.put("attm_url", subImages[i]);
                    map.put("attm_name", originalName);
                    map.put("attm_save_name", reName);
                    map.put("attm_category", "s_sub");
                    list1.add(map);
                }
            }
            result2 = aService.enrollProductImages(list1);

            if (result2 <= 0) {
                throw new AdminException("서브 이미지 등록에 실패했습니다.");
            }

        }

        String productNo = sc.getSelectProductNo();
        AdminProductImages frontImage = aService.searchFrontImage(productNo);

        HashMap<String, String> subMap = new HashMap<String, String>();
        subMap.put("attm_category", "s_sub");
        subMap.put("productNo", productNo);
        ArrayList<AdminProductImages> newSubImages = new ArrayList<AdminProductImages>();
        newSubImages = aService.searchImages(subMap);

        model.addAttribute("page", page);
        model.addAttribute("sc", sc);
        model.addAttribute("p", p);
        model.addAttribute("frontImage", frontImage);
        model.addAttribute("subImages", newSubImages);
        model.addAttribute("msg", "success");
        return "productUpdateDetailAdmin";
    }

    @PostMapping("/enrollCoupon.admin")
    public String enrollCoupon(@ModelAttribute AdminCoupon c, Model model) {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        AdminCoupon ac = new AdminCoupon();
        ac.setCouponOpenDate(formattedDate);
        ac.setCouponCloseDate(formattedDate);
        model.addAttribute("ac", ac);

        int result = aService.enrollCoupon(c);

        if (result <= 0) {
            throw new AdminException("쿠폰 등록에 실패했습니다.");
        }else{
            model.addAttribute("msg", "success");
        }
        
        return "couponMakingAdmin";
    }

    @GetMapping("/searchCoupon.admin")
    public String searchCoupon(Model model,
            @RequestParam("category") String category,
            @RequestParam("query") String query,
            @RequestParam("order") String order,
            @RequestParam("eventOngoing") String eventOngoing,
            @RequestParam("couponConditionList") ArrayList<String> couponConditionList,
            @RequestParam(value = "page", required = false) Integer page) {

        int currentPage = 1;
        if (page != null) {
            currentPage = page;
        }
        if (query == null) {
            query = "";
        }

        if (couponConditionList.contains("all")) {
            couponConditionList.remove("all");
            couponConditionList.add("download");
            couponConditionList.add("signup");
            couponConditionList.add("survey");
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("order", order);
        map.put("eventOngoing", eventOngoing);
        map.put("couponConditionList", couponConditionList);

        int listCount = aService.getSearchCouponListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminCoupon> list = new ArrayList<AdminCoupon>();
        list = aService.searchCoupon(pi, map);

        for (AdminCoupon a : list) {
            if (a.getCouponOpenDate() != null) {
                a.setCouponOpenDate(a.getCouponOpenDate().split(" ")[0]);
            }
            if (a.getCouponCloseDate() != null) {
                a.setCouponCloseDate(a.getCouponCloseDate().split(" ")[0]);
            }
        }

        if (list != null) {
            model.addAttribute("list", list);
            model.addAttribute("page", page);
            model.addAttribute("pi", pi);
            model.addAttribute("category", category);
            model.addAttribute("query", query);
            model.addAttribute("order", order);
            model.addAttribute("eventOngoing", eventOngoing);
            model.addAttribute("couponConditionList", couponConditionList);
        }

        return "couponManageAdmin";
    }

    @PostMapping("downloadCoupon")
    @ResponseBody
    private String downloadCoupon(@ModelAttribute AdminCoupon c, HttpSession session) {

        String memberNo = ((Member) session.getAttribute("loginUser")).getMemberNo();
        ArrayList<AdminCouponIssuance> couponIsList = aService.getCouponIssuanceList(memberNo);

        int check = 0;
        for (AdminCouponIssuance aci : couponIsList) {
            if (aci.getCouponNo().equals(c.getCouponNo())) {
                check++;
            }
        }

        int result = 0;
        if (check == 0) {
            String couponEndDate = null;

            if (c.getCouponPeriodType().equals("obtain")) {
                couponEndDate = "SYSDATE + " + c.getCouponPeriod();
            }
            if (c.getCouponPeriodType().equals("close")) {
                couponEndDate = "TO_DATE('" + c.getCouponCloseDate().split(" ")[0] + "', 'YY-MM-DD') +"
                        + c.getCouponPeriod();
            }
            if (c.getCouponPeriodType().equals("same")) {
                couponEndDate = "TO_DATE('" + c.getCouponCloseDate().split(" ")[0] + "', 'YY-MM-DD')";
            }

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("memberNo", memberNo);
            map.put("couponEndDate", couponEndDate);
            map.put("couponNo", c.getCouponNo());
            result = aService.downloadCoupon(map);
        }

        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("msg", "쿠폰이 다운로드 되었습니다.");
        } else {
            json.put("msg", "이미 다운로드 받은 쿠폰입니다.");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponName.admin")
    @ResponseBody
    public String changeCouponName(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponName") String couponName) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponName", couponName);

        int result = 0;
        result = aService.changeCouponName(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponName", couponName);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponContent.admin")
    @ResponseBody
    public String changeCouponContent(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponContent") String couponContent) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponContent", couponContent);

        int result = 0;
        result = aService.changeCouponContent(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponContent", couponContent);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponUseCondition.admin")
    @ResponseBody
    public String changeCouponUseCondition(@RequestParam("couponNo") String couponNo,
            @RequestParam("selectedCouponUseCondition") String selectedCouponUseCondition) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("selectedCouponUseCondition", selectedCouponUseCondition);

        int result = 0;
        result = aService.changeCouponUseCondition(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponUseCondition", selectedCouponUseCondition);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponDiscountRate.admin")
    @ResponseBody
    public String changeCouponDiscountRate(@RequestParam("couponNo") String couponNo,
            @RequestParam("selectedCouponDiscountRate") String selectedCouponDiscountRate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("selectedCouponDiscountRate", selectedCouponDiscountRate);

        int result = 0;
        result = aService.changeCouponDiscountRate(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponDiscountRate", selectedCouponDiscountRate);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponCondition.admin")
    @ResponseBody
    public String changeCouponCondition(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponCondition") String couponCondition) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponCondition", couponCondition);

        int result = 0;
        result = aService.changeCouponCondition(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponCondition", couponCondition);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponOpenClose.admin")
    @ResponseBody
    public String changeCouponOpenClose(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponOpenDate") String couponOpenDate,
            @RequestParam("couponCloseDate") String couponCloseDate) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponOpenDate", "" + couponOpenDate);
        map.put("couponCloseDate", "" + couponCloseDate);

        int result = 0;
        result = aService.changeCouponOpenClose(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponPeriodType.admin")
    @ResponseBody
    public String changeCouponPeriodType(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponPeriodType") String couponPeriodType) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponPeriodType", couponPeriodType);

        int result = 0;
        result = aService.changeCouponPeriodType(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("couponPeriodType", couponPeriodType);
        } else {
            json.put("error", "error");
        }
        return json.toString();

    }

    @GetMapping("/changeCouponPeriod.admin")
    @ResponseBody
    public String changeCouponPeriod(@RequestParam("couponNo") String couponNo,
            @RequestParam("couponPeriod") String couponPeriod) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);
        map.put("couponPeriod", couponPeriod);

        int result = 0;
        result = aService.changeCouponPeriod(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedCouponPeriod", couponPeriod);
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeCouponStatus.admin")
    @ResponseBody
    public String changeCouponStatus(@RequestParam("couponNo") String couponNo,
                                    @RequestParam("couponStatus") String couponStatus) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponNo", couponNo);

        if(couponStatus.equals("활성")){
            map.put("couponStatus", "N");
        }else if(couponStatus.equals("비활성")){
            map.put("couponStatus", "Y");
        }

        int result = 0;
        result = aService.changeCouponStatus(map);
        JSONObject json = new JSONObject();
        if (result > 0) {
            if(couponStatus.equals("활성")){
                json.put("changedCouponStatus", "비활성");
            }else if(couponStatus.equals("비활성")){
                json.put("changedCouponStatus", "활성");
            }
        } else {
            json.put("error", "error");
        }
        return json.toString();
    }

    @PostMapping("/enrollEvent.admin")
    public String enrollEvent(@ModelAttribute AdminEvent e, Model model,
                            @RequestParam(value = "frontImageUrl", required = false) String frontImage,
                            @RequestParam(value = "productNo", required = false) ArrayList<String> productNo) {
        
        if(productNo.size() > 0){
            HashMap<String, Object> map3 = new HashMap<String, Object>();
            map3.put("productNo", productNo);
            map3.put("eventStartDate", e.getEventStartDate());
            map3.put("eventEndDate", e.getEventEndDate());
            ArrayList<String> duplicateProductList = aService.checkEventProductDuplication(map3);
            if(duplicateProductList.size() > 0){
                String msgDetail = "/ ";
                for (String d : duplicateProductList) {
                    msgDetail += d + " / ";
                }
                model.addAttribute("msg", "error");
                model.addAttribute("msgDetail", msgDetail);
                return "eventMakingAdmin";
            }
        }else{
            model.addAttribute("msg", "error2");
            return "eventMakingAdmin";
        }

        int result1 = 0;
        result1 = aService.enrollEvent(e);

        if (result1 <= 0) {
            throw new AdminException("이벤트 등록에 실패했습니다.");
        }

        int result2 = 0;
        HashMap<String, String> map1 = new HashMap<String, String>();
        String originalName = frontImage.substring(frontImage.lastIndexOf("/") + 38);
        String reName = frontImage.substring(frontImage.lastIndexOf("/") + 1);
        map1.put("eventNo", e.getEventNo());
        map1.put("attm_url", frontImage);
        map1.put("attm_name", originalName);
        map1.put("attm_save_name", reName);
        map1.put("attm_category", "e_main");
        result2 = aService.enrollFrontImage(map1);

        if (result2 <= 0) {
            throw new AdminException("이벤트 등록에 실패했습니다.");
        }

        if(productNo.size() > 0){
            int result3 = 0;
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            map2.put("eventNo", e.getEventNo());
            map2.put("productNo", productNo);
            result3 = aService.enrollEventProducts(map2);

            if (result3 <= 0) {
                throw new AdminException("이벤트 등록에 실패했습니다.");
            }
        }
            
        model.addAttribute("msg", "success");
        return "eventMakingAdmin";
    }

    @GetMapping("/getSearchProductList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchProductList(@ModelAttribute AdminProductSearchCondition sc,
            @RequestParam(value = "page", required = false) Integer page) {

        int currentPage = 1;
        if (page != null) {
            currentPage = page;
        }

        if (sc.getQuery() == null) {
            sc.setQuery("");
        }

        ArrayList<String> productCategoryNoList = sc.getProductCategoryNoList();

        if (productCategoryNoList.contains("all")) {
            productCategoryNoList.remove("all");
            productCategoryNoList.add("P01");
            productCategoryNoList.add("P02");
            productCategoryNoList.add("P03");
            productCategoryNoList.add("P04");
            productCategoryNoList.add("P05");
            productCategoryNoList.add("P06");
        }
        sc.setProductCategoryNoList(productCategoryNoList);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sc", sc);

        int listCount = aService.getSearchListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminProduct> list = aService.searchProductAdmin(pi, map);

        Context context = new Context();

        if (!list.isEmpty()) {
            for (AdminProduct a : list) {
                if (a.getProductResistDate() != null) {
                    a.setProductResistDate(a.getProductResistDate().split(" ")[0]);
                }
                if (a.getProductUpdate() != null) {
                    a.setProductUpdate(a.getProductUpdate().split(" ")[0]);
                }
            }

            context.setVariable("list", list);
        }

        context.setVariable("sc", sc);
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/productSearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/getSearchEventList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchEventList(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam("category") String category,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam("selectEventStartDate") String selectEventStartDate,
            @RequestParam(value = "eventStartDateStart", required = false) String eventStartDateStart,
            @RequestParam(value = "eventStartDateEnd", required = false) String eventStartDateEnd,
            @RequestParam("selectEventEndDate") String selectEventEndDate,
            @RequestParam(value = "eventEndDateStart", required = false) String eventEndDateStart,
            @RequestParam(value = "eventEndDateEnd", required = false) String eventEndDateEnd,
            @RequestParam("selectEventActivity") String selectEventActivity,
            @RequestParam("order") String order) {

        int currentPage = 1;
        if (page != null) {
            currentPage = page;
        }

        if (query == null) {
            query = "";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("selectEventStartDate", selectEventStartDate);
        map.put("eventStartDateStart", eventStartDateStart);
        map.put("eventStartDateEnd", eventStartDateEnd);
        map.put("selectEventEndDate", selectEventEndDate);
        map.put("eventEndDateStart", eventEndDateStart);
        map.put("eventEndDateEnd", eventEndDateEnd);
        map.put("selectEventActivity", selectEventActivity);
        map.put("order", order);

        int listCount = aService.getSearchEventListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminEvent> list = new ArrayList<AdminEvent>();
        list = aService.getSearchEventList(pi, map);

        Context context = new Context();

        if (list != null) {
            for (AdminEvent e : list) {
                if (e.getEventStartDate() != null) {
                    e.setEventStartDate(e.getEventStartDate().split(" ")[0]);
                }
                if (e.getEventEndDate() != null) {
                    e.setEventEndDate(e.getEventEndDate().split(" ")[0]);
                }
            }

            context.setVariable("list", list);
        }
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/eventSearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/changeEvent.admin")
    @ResponseBody
    public String changeEvent(@RequestParam(value="eventNo", required=false) String eventNo, 
                            @RequestParam(value="selectedValue", required=false) String selectedValue,
                            @RequestParam(value="selectedEventStartDate", required=false) String eventStartDate,
                            @RequestParam(value="selectedEventEndDate", required=false) String eventEndDate,
                            @RequestParam(value="eventType", required=false) String eventType){
        
                                
        JSONObject json = new JSONObject();  
        ArrayList<String> productNoList = aService.getEventProductNoList(eventNo);
        
        if(productNoList.size() > 0 && !selectedValue.equals("활성")){
            HashMap<String, Object> map4 = new HashMap<String, Object>();
            map4.put("eventNo", eventNo);
            map4.put("productNo", productNoList);
            map4.put("eventStartDate", eventStartDate);
            map4.put("eventEndDate", eventEndDate);
            ArrayList<String> duplicateProductList = aService.checkEventProductDuplication(map4);
            if(duplicateProductList.size() > 0){
                String msgDetail = "/ ";
                for (String d : duplicateProductList) {
                    msgDetail += d + " / ";
                }
                json.put("error", "error");
                json.put("errorMsg", msgDetail);
                return json.toString();
            }
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("table", "event");
        map.put("columnName", eventType);
        
        if(selectedValue.equals("활성")){
            map.put("selectedValue", "N");
        }else if(selectedValue.equals("비활성")){
            map.put("selectedValue", "Y");
        }else{
            map.put("selectedValue", selectedValue);
        }

        map.put("condition", "event_no");
        map.put("selectedCondition", eventNo);
        
        int result = 0;
        result = aService.updateTable(map);


        if(result > 0){
            if(selectedValue.equals("활성")){
                json.put("changedValue", "비활성");
            }else if(selectedValue.equals("비활성")){
                json.put("changedValue", "활성");
            }else{
                json.put("changedValue", selectedValue);
            }
        }else{
            json.put("error", "error2");
        }
        return json.toString();
    }


    @GetMapping("/getSearchBoardList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchBoardList(@RequestParam("page") Integer currentPage,
            @RequestParam("category") String category,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam("status") String status,
            @RequestParam("order") String order) {

        if (query == null) {
            query = "";
        }
        
        if (status.equals("Y")) {
            status = "(board_isdeleted = 'N' and board_ishidden = 'N')";
        } else if (status.equals("N")) {
            status = "(board_isdeleted = 'Y' or board_ishidden = 'Y')";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("status", status);
        map.put("order", order);

        int listCount = aService.getSearchBoardListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminBoard> list = aService.getSearchBoardList(pi, map);

        Context context = new Context();
        if (!list.isEmpty()) {
            for (AdminBoard a : list) {
                if (a != null && a.getBoardEnrollDate() != null) {
                    a.setBoardEnrollDate(a.getBoardEnrollDate().split(" ")[0]);
                }
                if (a != null && a.getBoardModifyDate() != null) {
                    a.setBoardModifyDate(a.getBoardModifyDate().split(" ")[0]);
                }
            }
            context.setVariable("list", list);
        }
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/boardSearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/getSearchReplyList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchReplyList(@RequestParam("page") Integer currentPage,
            @RequestParam("category") String category,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam("status") String status,
            @RequestParam("order") String order) {

        if (query == null) {
            query = "";
        }

        if (status.equals("Y")) {
            status = "(reply_isdeleted = 'N' and reply_ishidden = 'N')";
        } else if (status.equals("N")) {
            status = "(reply_isdeleted = 'Y' or reply_ishidden = 'Y')";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("status", status);
        map.put("order", order);

        int listCount = aService.getSearchReplyListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminReply> list = aService.getSearchReplyList(pi, map);

        Context context = new Context();
        if (!list.isEmpty()) {
            for (AdminReply a : list) {
                if (a != null && a.getReplyEnrollDate() != null) {
                    a.setReplyEnrollDate(a.getReplyEnrollDate().split(" ")[0]);
                }
                if (a != null && a.getReplyModifyDate() != null) {
                    a.setReplyModifyDate(a.getReplyModifyDate().split(" ")[0]);
                }
            }
            context.setVariable("list", list);
        }
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/replySearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/changeBoard.admin")
    @ResponseBody
    public String changeBoard(@RequestParam("boardNo") String boardNo,
            @RequestParam("boardType") String boardType,
            @RequestParam("selectedValue") String selectedValue) {

        if (boardType.equals("boardStatus")) {
            switch (selectedValue) {
                case "active":
                    selectedValue = "N";
                    boardType = "board_ishidden = 'N', board_isdeleted";
                    break;
                case "hidden":
                    selectedValue = "Y";
                    boardType = "board_ishidden";
                    break;
                case "delete":
                    selectedValue = "Y";
                    boardType = "board_isdeleted";
                    break;
            }
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("table", "community_board");
        map.put("columnName", boardType);
        map.put("selectedValue", selectedValue);
        map.put("condition", "board_no");
        map.put("selectedCondition", boardNo);

        int result = 0;
        result = aService.updateTable(map);

        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedValue", selectedValue);
        }else{
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeMember.admin")
    @ResponseBody
    public String changeMember(@RequestParam("memberNo") String memberNo,
            @RequestParam("memberType") String memberType,
            @RequestParam("selectedValue") String selectedValue) {

        String changedValue = "";
        if (memberType.equals("member_is_blacklist")) {
            switch (selectedValue) {
                case "정지하기":
                    selectedValue = "Y";
                    changedValue = "정지해제";
                    break;
                case "정지해제":
                    selectedValue = "N";
                    changedValue = "정지하기";
                    break;
            }
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("table", "member");
        map.put("columnName", memberType);
        map.put("selectedValue", selectedValue);
        map.put("condition", "member_no");
        map.put("selectedCondition", memberNo);

        int result = 0;
        result = aService.updateTable(map);

        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedValue", changedValue);
        }else{
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/changeReply.admin")
    @ResponseBody
    public String changeReply(@RequestParam("replyNo") String replyNo,
            @RequestParam("replyType") String replyType,
            @RequestParam("selectedValue") String selectedValue) {

        if (replyType.equals("replyStatus")) {
            switch (selectedValue) {
                case "active":
                    selectedValue = "N";
                    replyType = "reply_ishidden = 'N', reply_isdeleted";
                    break;
                case "hidden":
                    selectedValue = "Y";
                    replyType = "reply_ishidden";
                    break;
                case "delete":
                    selectedValue = "Y";
                    replyType = "reply_isdeleted";
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("table", "community_reply");
        map.put("columnName", replyType);
        map.put("selectedValue", selectedValue);
        map.put("condition", "reply_no");
        map.put("selectedCondition", replyNo);

        int result = 0;
        result = aService.updateTable(map);

        JSONObject json = new JSONObject();
        if (result > 0) {
            json.put("changedValue", selectedValue);
        }else{
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/getSearchProductOrderList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchProductOrderList(@RequestParam("page") Integer currentPage,
            @RequestParam("category") String category,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam("status") String status,
            @RequestParam("order") String order) {

        if (query == null) {
            query = "";
        }

        if (status.equals("Y")) {
            status = "(order_delivery_date is not null)";
        } else if (status.equals("N")) {
            status = "(order_delivery_date is null)";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("status", status);
        map.put("order", order);

        int listCount = aService.getSearchProductOrderListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminProductOrder> list = aService.getSearchProductOrderList(pi, map);

        Context context = new Context();
        if (!list.isEmpty()) {
            for (AdminProductOrder a : list) {
                if (a.getOrderDate() != null) {
                    a.setOrderDate(a.getOrderDate().split(" ")[0]);
                }
                if (a.getOrderDeliveryDate() != null) {
                    a.setOrderDeliveryDate(a.getOrderDeliveryDate().split(" ")[0]);
                }
            }
            context.setVariable("list", list);
        }
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/productOrderSearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/deliveryPopup.admin")
    public String deliveryPopup(@RequestParam("cartNo") String cartNo, Model model) {

        ArrayList<AdminProductOrder> list = aService.getCartList(cartNo);

        int total = 0;
        if (!list.isEmpty()) {
            for (AdminProductOrder a : list) {
                total += Integer.parseInt(a.getOrderPrice());
                if (a != null && a.getOrderDate() != null) {
                    a.setOrderDate(a.getOrderDate().split(" ")[0]);
                }
                if (a != null && a.getOrderDeliveryDate() != null) {
                    a.setOrderDeliveryDate(a.getOrderDeliveryDate().split(" ")[0]);
                }
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("total", total);

        return "popup/deliveryPopupAdmin";
    }

    @PostMapping("/updateProductOrder.admin")
    @ResponseBody
    public String updateProductOrder(@RequestParam("orderDeliveryCourier") String orderDeliveryCourier,
                                    @RequestParam("orderDeliveryNo") String orderDeliveryNo,
                                    @RequestParam("cartNo") String cartNo){
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("table", "product_order");
        map.put("columnName",
                "order_delivery_date = sysdate, order_delivery_no = '" + orderDeliveryNo + "', order_delivery_courier");
        map.put("selectedValue", orderDeliveryCourier);
        map.put("condition", "cart_no");
        map.put("selectedCondition", cartNo);

        int result = 0;
        result = aService.updateTable(map);

        JSONObject json = new JSONObject();
        if (result > 0) {
        }else{
            json.put("error", "error");
        }
        return json.toString();
    }

    @GetMapping("/getSearchQuestionBoardList.admin")
    @ResponseBody
    public ResponseEntity<String> getSearchQuestionBoardList(@RequestParam("page") Integer currentPage,
            @RequestParam("category") String category,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam("status") String status,
            @RequestParam("order") String order) {

        if (query == null) {
            query = "";
        }

        if (status.equals("Y")) {
            status = "(question_answer is not null)";
        } else if (status.equals("N")) {
            status = "(question_answer is null)";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("query", query);
        map.put("status", status);
        map.put("order", order);

        int listCount = aService.getSearchQuestionBoardListCount(map);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<AdminQuestionBoard> list = aService.getSearchQuestionBoardList(pi, map);

        Context context = new Context();
        if (!list.isEmpty()) {
            for (AdminQuestionBoard a : list) {
                if (a.getQuestionUpdateDate() != null) {
                    a.setQuestionUpdateDate(a.getQuestionUpdateDate().split(" ")[0]);
                }
            }
            context.setVariable("list", list);
        }
        context.setVariable("pi", pi);
        String html = templateEngine.process("list/questionBoardSearchList", context);
        return ResponseEntity.ok(html);
    }

    @GetMapping("/questionBoardPopup.admin")
    public String questionBoardPopup(@RequestParam("questionNo") String questionNo, Model model) {

        AdminQuestionBoard a = aService.getQuestionBoardDetail(questionNo);

        if (a.getQuestionUpdateDate() != null) {
            a.setQuestionUpdateDate(a.getQuestionUpdateDate().split(" ")[0]);
        }

        model.addAttribute("a", a);

        return "popup/questionBoardPopupAdmin";
    }

    @PostMapping("/updateQuestionBoard.admin")
    @ResponseBody
    public String updateQuestionBoard(@RequestParam("questionAnswer") String questionAnswer,
                                    @RequestParam("questionNo") String questionNo){
        
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("table", "question_board");
        map.put("columnName", "question_answer");
        map.put("selectedValue", questionAnswer);
        map.put("condition", "question_no");
        map.put("selectedCondition", questionNo);

        int result = 0;
        result = aService.updateTable(map);

        JSONObject json = new JSONObject();
        if (result > 0) {
        }else{
            json.put("error", "error");
        }
        return json.toString();
    }

    @PostMapping("/eventUpdateDetail.admin")
    public String eventUpdateDetailAdmin(@RequestParam("eventNo") String eventNo, Model model){
        AdminEvent e = aService.getEvent(eventNo);

        if(e == null){

        }

        e.setEventStartDate(e.getEventStartDate().split(" ")[0]);
        e.setEventEndDate(e.getEventEndDate().split(" ")[0]);
        
        ArrayList<String> eventProductNoList = aService.getEventProductNoList(eventNo);

        if(eventProductNoList.size() == 0){
            throw new AdminException("이벤트 정보가 없습니다.");
        }

        ArrayList<AdminProduct> list = aService.getEventProductList(eventProductNoList);

        if(list == null){
            throw new AdminException("상품 정보가 없습니다.");
        }


        model.addAttribute("e", e);
        model.addAttribute("list", list);
        return "eventUpdateDetailAdmin";
    }

    @PostMapping("/updateEventDetail.admin")
    public String updateEventDetail(@ModelAttribute AdminEvent e, Model model,
                                    @RequestParam(value = "frontImageUrlBefore", required = false) String frontImageBefore,
                                    @RequestParam(value = "frontImageUrl", required = false) String frontImage,
                                    @RequestParam(value = "productNoBefore", required = false) ArrayList<String> productNoBefore,
                                    @RequestParam(value = "productNo", required = false) ArrayList<String> productNoAfter){
        
        ArrayList<String> eventProductNoList = aService.getEventProductNoList(e.getEventNo());
        ArrayList<AdminProduct> list = aService.getEventProductList(eventProductNoList);
        model.addAttribute("list", list);
        
        if(productNoAfter.size() > 0){
            HashMap<String, Object> map4 = new HashMap<String, Object>();
            map4.put("productNo", productNoAfter);
            map4.put("eventNo", e.getEventNo());
            map4.put("eventStartDate", e.getEventStartDate());
            map4.put("eventEndDate", e.getEventEndDate());
            ArrayList<String> duplicateProductList = aService.checkEventProductDuplication(map4);
            if(duplicateProductList.size() > 0){
                String msgDetail = "/ ";
                for (String d : duplicateProductList) {
                    msgDetail += d + " / ";
                }
                model.addAttribute("e", e);
                model.addAttribute("msg", "error");
                model.addAttribute("msgDetail", msgDetail);
                return "eventUpdateDetailAdmin";
            }
        }else{
            model.addAttribute("e", e);
            model.addAttribute("msg", "error2");
            return "eventUpdateDetailAdmin";
        }
        
        int result1 = 0;
        result1 = aService.updateEventDetail(e);

        if (result1 <= 0) {
            model.addAttribute("e", e);
            model.addAttribute("msg", "error3");
            return "eventUpdateDetailAdmin";
        }

        if(!frontImageBefore.equals(frontImage)){
            int result2 = 0;
            ArrayList<String> deleteImage = new ArrayList<String>();
            deleteImage.add(frontImageBefore);
            result2 = aService.deleteImage(deleteImage);
            
            if (result2 <= 0) {
                model.addAttribute("e", e);
                model.addAttribute("msg", "error3");
                return "eventUpdateDetailAdmin";
            }

            int result3 = 0;
            HashMap<String, String> map1 = new HashMap<String, String>();
            String originalName = frontImage.substring(frontImage.lastIndexOf("/") + 38);
            String reName = frontImage.substring(frontImage.lastIndexOf("/") + 1);
            map1.put("eventNo", e.getEventNo());
            map1.put("attm_url", frontImage);
            map1.put("attm_name", originalName);
            map1.put("attm_save_name", reName);
            map1.put("attm_category", "e_main");
            result3 = aService.enrollFrontImage(map1);
    
            if (result3 <= 0) {
                model.addAttribute("e", e);
                model.addAttribute("msg", "error3");
                return "eventUpdateDetailAdmin";
            }
        }

        boolean check = false;

        if (productNoBefore.size() == productNoAfter.size()) {
            for (String bp : productNoBefore) {
                if (!productNoAfter.contains(bp)) {
                    check = true;
                    break;
                }
            }
        } else {
            check = true;
        }

        if (check) {
            ArrayList<String> deleteProductNo = new ArrayList<String>();
            ArrayList<String> insertProductNo = new ArrayList<String>();
            for(String bp : productNoBefore){
                if(!productNoAfter.contains(bp)){
                    deleteProductNo.add(bp);
                }
            }
            for(String ap : productNoAfter){
                if(!productNoBefore.contains(ap)){
                    insertProductNo.add(ap);
                }
            }
    
            if(insertProductNo != null && insertProductNo.size() > 0){
                int result4 = 0;
                HashMap<String, Object> map2 = new HashMap<String, Object>();
                map2.put("eventNo", e.getEventNo());
                map2.put("productNo", insertProductNo);
                result4 = aService.enrollEventProducts(map2);
            
                if (result4 <= 0) {
                    model.addAttribute("e", e);
                    model.addAttribute("msg", "error3");
                    return "eventUpdateDetailAdmin";
                }
            }
            
            if(deleteProductNo != null && deleteProductNo.size() > 0){
                int result5 = 0;
                HashMap<String, Object> map3 = new HashMap<String, Object>();
                map3.put("eventNo", e.getEventNo());
                map3.put("productNo", deleteProductNo);
                result5 = aService.deleteEventProducts(map3);
            
                if (result5 <= 0) {
                    model.addAttribute("e", e);
                    model.addAttribute("msg", "error3");
                    return "eventUpdateDetailAdmin";
                }
            }
        } 

        model.addAttribute("msg", "success");
        return "eventManageAdmin";
    }

}
