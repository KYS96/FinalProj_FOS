package com.kh.slumber.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.slumber.admin.model.service.AdminServiceTempStatistic;
import com.kh.slumber.admin.model.vo.AdminProduct;
import com.kh.slumber.admin.model.vo.AdminProductImages;
import com.kh.slumber.admin.model.vo.AdminProductSearchCondition;
import com.kh.slumber.admin.model.vo.AdminStatisticBoardTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticProductChartTemp;
import com.kh.slumber.admin.model.vo.AdminStatisticSpendingInfoTemp;
import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;

@Controller
public class AdminControllerTempStatistic {

  @Autowired
  private AdminServiceTempStatistic atsService;

  @Autowired
  private TemplateEngine templateEngine;

  @GetMapping("statisticMain.admin")

  public String statisticMainAdmin() {
    return "statisticMainAdmin";
  }

  @RequestMapping(value = "getMemberDatas.admin", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public String getMemberDatas() {

    // 신규 회원 수 (최근 2주 동안 가입한 사람의 수)
    int newMemberTotal = atsService.getNewMemberTotal();

    // 방문 회원 수 ?

    // 탈퇴 회원 수
    int DisabledMemberTotal = atsService.getDisabledMemberTotal();

    // 총 회원 수
    int entireMemberTotal = atsService.getEntireMemberTotal();

    JSONObject jobj = new JSONObject();

    jobj.put("newMemberTotal", newMemberTotal);
    jobj.put("DisabledMemberTotal", DisabledMemberTotal);
    jobj.put("entireMemberTotal", entireMemberTotal);

    return jobj.toString();
  }

  @RequestMapping("getMemberSpendingDatas.admin")
  @ResponseBody
  public ResponseEntity<String> getMemberSpendingDatas(
      @RequestParam(value = "page", required = false) Integer currentPage) {

    if (currentPage == null) {
      currentPage = 1;
    }

    // 멤버 인원 다 가져오기
    int listCount = atsService.getMemberSpendingDatasListCount();
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
    ArrayList<AdminStatisticSpendingInfoTemp> sList = atsService.getMemberSpendingDatas(pi);

    String resultHtml = "";
    Context context = new Context();

    for (AdminStatisticSpendingInfoTemp s : sList) {

      if (s.getMemberAddr() == null) {
        s.setMemberAddr("주소없음");
      }
      context.setVariable("memberNo", s.getMemberNo());
      context.setVariable("memberId", s.getMemberId());
      context.setVariable("memberName", s.getMemberName());
      context.setVariable("memberPhone", s.getMemberPhone());
      context.setVariable("memberAddr", s.getMemberAddr());
      context.setVariable("totalSpending", s.getTotalSpending());

      resultHtml += templateEngine.process("memberSpendingRanking", context);
    }

    return ResponseEntity.ok(resultHtml);
  }

  @RequestMapping("getMemberSpendingPaging.admin")
  @ResponseBody
  public ResponseEntity<String> getMemberSpendingPaging(
      @RequestParam(value = "page", required = false) Integer currentPage) {
    if (currentPage == null) {
      currentPage = 1;
    }

    int listCount = atsService.getMemberSpendingDatasListCount();
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);

    String resultHtml = "";
    Context context = new Context();
    
    context.setVariable("pi", pi);

    resultHtml += templateEngine.process("memberSpendingPaging", context);

    return ResponseEntity.ok(resultHtml);
  }

  @RequestMapping("getBoardViewRanking.admin")
  @ResponseBody
  public ResponseEntity<String> getBoardViewRanking(
      @RequestParam(value = "page", required = false) Integer currentPage) {

    if (currentPage == null) {
      currentPage = 1;
    }

    // 조회수 높은 기준으로 커뮤니티,공지사항 글 가져오기
    int listCount = atsService.getListCount();
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 15);

    ArrayList<AdminStatisticBoardTemp> list = atsService.getBoardViewRanking(pi);

    String resultHtml = "";
    Context context = new Context();
    String boardType = "";

    for (AdminStatisticBoardTemp a : list) {
      boardType = a.getBoardType();
      if (boardType.equals("free_and_humor")) {
        boardType = "자유, 유머";
      } else if (boardType.equals("tips_and_info")) {
        boardType = "꿀팁, 정보";
      } else {
        boardType = "기타";
      }

      context.setVariable("sortion", a.getSortion());
      context.setVariable("boardType", boardType);
      context.setVariable("boardNo", a.getBoardNo());
      context.setVariable("boardTitle", a.getBoardTitle());
      context.setVariable("memberId", a.getMemberId());
      context.setVariable("views", a.getViews());
      context.setVariable("boardIsdeleted", a.getBoardIsdeleted());

      resultHtml += templateEngine.process("viewRanking", context);
    }

    return ResponseEntity.ok(resultHtml);

  }

  @RequestMapping("viewRankingPagination")
  @ResponseBody
  public ResponseEntity<String> pagination(@RequestParam(value = "page", required = false) Integer currentPage) {
    if (currentPage == null) {
      currentPage = 1;
    }

    int listCount = atsService.getListCount();
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 15);

    String resultHtml = "";
    Context context = new Context();

    context.setVariable("currentPage", pi.getCurrentPage());
    context.setVariable("startPage", pi.getStartPage());
    context.setVariable("endPage", pi.getEndPage());
    context.setVariable("maxPage", pi.getMaxPage());

    resultHtml += templateEngine.process("viewRankingPaging", context);

    return ResponseEntity.ok(resultHtml);
  }

  @RequestMapping("loadProductSales.admin")
  @ResponseBody
  public ResponseEntity<String> loadProductSales(@RequestParam(value = "page", required = false) Integer currentPage) {
    // 상품 가져오기 (판매량 순위 정렬)
    int listCount = atsService.getProductSalesListCount();

    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8);

    ArrayList<AdminProduct> list = atsService.getProductSalesList(pi);

    String resultHtml = "";
    Context context = new Context();

    for (AdminProduct ap : list) {
      context.setVariable("productNo", ap.getProductNo());
      context.setVariable("productImage", ap.getProductImage());
      context.setVariable("productCategoryName", ap.getProductCategoryName());
      context.setVariable("productName", ap.getProductName());
      context.setVariable("productPrice", ap.getProductPrice());
      context.setVariable("productSales", ap.getProductSales());
      context.setVariable("productStock", ap.getProductStock());
      context.setVariable("productStatus", ap.getProductStatus());

      resultHtml += templateEngine.process("viewProductSales", context);
    }

    return ResponseEntity.ok(resultHtml);
  }

  @RequestMapping("loadProductSalesPaging")
  @ResponseBody
  public ResponseEntity<String> loadProductSalesPaging(
      @RequestParam(value = "page", required = false) Integer currentPage) {

    int listCount = atsService.getProductSalesListCount();
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8);

    String resultHtml = "";
    Context context = new Context();

    context.setVariable("currentPage", pi.getCurrentPage());
    context.setVariable("startPage", pi.getStartPage());
    context.setVariable("endPage", pi.getEndPage());
    context.setVariable("maxPage", pi.getMaxPage());

    resultHtml += templateEngine.process("viewProductSalesPaging", context);

    return ResponseEntity.ok(resultHtml);
  }

  // 도넛 차트
  @RequestMapping(value = "getSalesChartDatas.admin", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public String getSalesChartDatas() {
    // 차트 정보 가져오기
    ArrayList<AdminStatisticProductChartTemp> list = atsService.getSalesChartDatas();

    JSONArray jarr = new JSONArray();

    for (AdminStatisticProductChartTemp a : list) {
      JSONObject jobj = new JSONObject();

      jobj.put("productCategoryNo", a.getProductCategoryNo());
      jobj.put("productCategoryName", a.getProductCategoryName());
      jobj.put("sumProductSales", a.getSumProductSales());

      jarr.put(jobj);
    }
    // 객체배열 형태
    return jarr.toString();
  }

  @RequestMapping("goToBoard.admin")
  public String goToBoard(@RequestParam("boardNo") int boardNo, @RequestParam("sortion") String sortion,
      RedirectAttributes ra) {
    String newgBoardNo = Integer.toString(boardNo);

    Properties prop = new Properties();

    prop.put("newgBoardNo", newgBoardNo);
    prop.put("sortion", sortion);

    AdminStatisticBoardTemp ats = atsService.goToBoard(prop);

    if (ats != null) {
      if (ats.getSortion().equals("커뮤니티")) {
        ra.addAttribute("no", ats.getBoardNo());
        return "redirect:post.comm";
      } else {
        ra.addAttribute("empBoardNo", ats.getBoardNo());
        return "redirect:noticeDetail.no";
      }
    } else {
      return null;
    }
  }

  @RequestMapping("productUpdateDetailAdmin.admin")
  public String goToUpdatePage(@RequestParam("productNo") String productNo,
      Model model, @RequestParam(value = "currentPage", required = false) Integer page) {

    if (page == null) {
      page = 1;
    }

    AdminProductSearchCondition sc = new AdminProductSearchCondition();

    // 상품 번호로 상품 정보 가져오기.
    AdminProduct p = atsService.getProductInfo(productNo);

    // 수정하고자 하는 사진의 경로와 이름 받아옴
    AdminProductImages frontImage = atsService.getFrontImage(productNo);
    // 서브 이미지들
    HashMap<String, String> subMap = new HashMap<String, String>();
    subMap.put("attm_category", "s_sub");
    subMap.put("productNo", productNo);
    ArrayList<AdminProductImages> subImages = new ArrayList<AdminProductImages>();
    subImages = atsService.getSearchImages(subMap);

    HashMap<String, String> detailMap = new HashMap<String, String>();
    detailMap.put("attm_category", "s_detail");
    detailMap.put("productNo", productNo);
    ArrayList<AdminProductImages> detailImages = new ArrayList<AdminProductImages>();
    detailImages = atsService.getSearchImages(detailMap);

    sc.setSelectProductNo(p.getProductNo());
    sc.setCategory(p.getProductCategoryName());

    model.addAttribute("page", page);
    model.addAttribute("sc", sc);
    model.addAttribute("p", p);
    model.addAttribute("frontImage", frontImage);
    model.addAttribute("subImages", subImages);
    model.addAttribute("detailImages", detailImages);

    return "productUpdateDetailAdmin";
  }

}
