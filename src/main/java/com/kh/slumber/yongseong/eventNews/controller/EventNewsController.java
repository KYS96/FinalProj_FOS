package com.kh.slumber.yongseong.eventNews.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.eventNews.model.service.EventNewsService;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNews;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsFrontImages;
import com.kh.slumber.yongseong.eventNews.model.vo.MarketEventNewsOnly;

@Controller
public class EventNewsController {

  @Autowired
  private EventNewsService eService;

  @Autowired
  private TemplateEngine templateEngine;

  // 메인화면 이벤트 리스트(캐러셀 광고 뜨는 것)
  @RequestMapping(value = "eventNewsListMain.ens", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public String eventNewsMain() {

    JSONArray jarr = new JSONArray();

    // 이벤트 정보 가져오기('현재 진행중인 이벤트'만 가져오기)
    ArrayList<MarketEventNewsOnly> eList = eService.getEventNewsMainList();

    // 이벤트 번호 받아서 썸네일 받아오기
    ArrayList<Integer> eventNo = new ArrayList<>();

    for (MarketEventNewsOnly me : eList) {
      eventNo.add(me.getEventNoOnly());
    }

    if (eventNo.isEmpty()) {
      System.out.println("eventNo : " + eventNo);
      return jarr.toString(); // 가져올 섬네일이 없을 때는 빈 배열을 반환
    }

    ArrayList<MarketEventNewsFrontImages> frontImagesList = eService.getFrontImagesList(eventNo);

    // 섬네일 이미지 갖고오기
    ArrayList<String> thumbnail = new ArrayList<>();
    for (MarketEventNewsFrontImages fi : frontImagesList) {
      thumbnail.add(fi.getMarketAttmURL());
    }

    // 섬네일 데이터 저장
    for (MarketEventNewsOnly e : eList) {
      JSONObject jobj = new JSONObject();

      String eventStartDate = String.format("%1$tY-%1$tm-%1$td", e.getEventStartDateOnly());
      String eventEndDate = String.format("%1$tY-%1$tm-%1$td", e.getEventEndDateOnly());
      String eventThumbnail = null;

      for (MarketEventNewsFrontImages fi : frontImagesList) {
        if (e.getEventNoOnly() == fi.getMarketAttmRefNo()) {
          eventThumbnail = fi.getMarketAttmURL();
          System.out.println(eventThumbnail);
          break;
        }
      }

      jobj.put("eventNo", e.getEventNoOnly());
      jobj.put("eventTitle", e.getEventTitleOnly());
      jobj.put("eventContent", e.getEventContentOnly());
      jobj.put("eventCode", e.getEventCodeOnly());
      jobj.put("eventStartDate", eventStartDate);
      jobj.put("eventEndDate", eventEndDate);
      jobj.put("eventThumbnail", eventThumbnail);

      jarr.put(jobj);
    }

    return jarr.toString();
  }

  // 수정하기
  // 이벤트 리스트 가져오기 (상세보기 전)
  @RequestMapping("eventNewsList.ens")
  public String eventNewsList(
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "page", required = false) Integer currentPage,
      Model model) {

    // 이벤트 리스트 탭 들어오자마자 전체 검색 결과 확인
    if (search == null) {
      search = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    Properties prop = new Properties();
    prop.setProperty("search", search);

    // 들어오자마자 목록 바로 보이 도록 - 총 개수 가져오기
    int listCount = eService.selectOnlyEventNewsSearchListCount(prop);

    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    int ep = Pagination.getPageInfo(currentPage, listCount, 10).getEndPage();

    pagingArrange(pi, currentPage, ep);

    ArrayList<MarketEventNewsOnly> eList = eService.selectOnlyEventNewsSearchList(pi, prop);

    Date eventEndDateOnly = null;

    int eventNoOnly = 0;
    for (MarketEventNewsOnly e : eList) {
      eventNoOnly = e.getEventNoOnly();
      eventEndDateOnly = e.getEventEndDateOnly();
    }

    if (!eList.isEmpty()) {
      model.addAttribute("eList", eList);
      model.addAttribute("search", search);
      model.addAttribute("pi", pi);
      model.addAttribute("page", currentPage);
      model.addAttribute("eventNoOnly", eventNoOnly);
      model.addAttribute("eventEndDateOnly", eventEndDateOnly);

      return "eventNewsList";
    } else {
      model.addAttribute("eList", eList);
      model.addAttribute("search", search);
      model.addAttribute("page", currentPage);
      model.addAttribute("eventEndDateOnly", eventEndDateOnly);

      return "eventNewsList";
    }
  }

  @GetMapping("eventNewsDetail.ens")
  public String eventNewsListDetail(
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "eventNo", required = false) Integer eventNo,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "productSearchPage", required = false) Integer currentPage,
      Model model) {

    // 이벤트 리스트 탭 들어오자마자 전체 검색 결과 확인
    // 들어온 eventNo에 해당하는 이벤트 상품들 가져오기

    if (page == null || page < 0) {
      page = 1;
    }

    if (search == null) {
      search = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    // 총 몇개의 이벤트 상품이 등록됐는지 개수 가져오기 (조건은 eventNo임) (listCount)
    int listCount = eService.selectEventNewsListCountDetail(eventNo);

    // eventNo에 맞는 진행 중인 상품을 토대로 페이징
    PageInfo productPi = Pagination.getPageInfo(currentPage, listCount, 6);
    int ep = Pagination.getPageInfo(currentPage, listCount, 6).getEndPage();

    pagingArrange(productPi, currentPage, ep);

    // 이벤트 중인 제품들의 정보를 가져오기
    ArrayList<MarketEventNews> eList = eService.selectEventNewsListDetail(eventNo, productPi);

    String eventTitle = "";
    String eventContent = "";
    String eventStartDate = "";
    String eventEndDate = "";
    int discountedPrice = 0;

    for (MarketEventNews e : eList) {
      eventStartDate = String.format("%1$tY-%1$tm-%1$td", e.getEventStartDate());
      eventEndDate = String.format("%1$tY-%1$tm-%1$td", e.getEventEndDate());
      eventTitle = e.getEventTitle();
      eventContent = e.getEventContent();

      if (e.getEventCode() > 100) {
        discountedPrice = e.getProductPrice() - e.getEventCode();
      } else {
        float discountedPercentage = (100 - e.getEventCode()) / 100.0f;
        discountedPrice = (int) (e.getProductPrice() * discountedPercentage);
      }
      e.setDiscountedPrice(discountedPrice);
    }

    if (!eList.isEmpty()) {
      model.addAttribute("productPi", productPi);
      model.addAttribute("eList", eList);
      model.addAttribute("search", search);
      model.addAttribute("eventNo", eventNo);
      model.addAttribute("page", page);
      model.addAttribute("productSearchPage", currentPage);
      model.addAttribute("eventStartDate", eventStartDate);
      model.addAttribute("eventEndDate", eventEndDate);
      model.addAttribute("eventTitle", eventTitle);
      model.addAttribute("eventContent", eventContent);

      return "eventNewsDetail";
    } else {
      model.addAttribute("eList", eList);
      model.addAttribute("page", currentPage);
      model.addAttribute("productSearchPage", currentPage);

      return "eventNewsDetail";
    }
  }

  // ajax 사용
  @RequestMapping(value = "eventListCondition.ens", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public String eventListCondition(Integer num, Integer eventNo) {

    int currentPage = 1;

    // eventNo에 해당하는 이벤트 중 총 몇개의 이벤트 상품이 등록됐는지 개수 가져오기 (listCount)
    int listCount = eService.selectEventNewsListCountDetail(eventNo);

    // 이벤트 진행 중인 상품을 토대로 페이징
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 100);

    // 들어온 숫자가 1이면 가격 낮은순
    // 들어온 숫자가 2이면 가격 높은순
    // 들어온 숫자가 3이면 제품 최신 등록순
    ArrayList<MarketEventNews> eList = eService.selectEventNewsListDetail(num, eventNo, pi);

    JSONArray jarr = new JSONArray();
    int discountedPrice = 0;

    for (MarketEventNews e : eList) {
      String eventStartDate = String.format("%1$tY-%1$tm-%1$td", e.getEventStartDate());
      String eventEndDate = String.format("%1$tY-%1$tm-%1$td", e.getEventEndDate());

      if (e.getEventCode() > 100) {
        discountedPrice = e.getProductPrice() - e.getEventCode();
      } else {
        float discountedPercentage = (100 - e.getEventCode()) / 100.0f;
        discountedPrice = (int) (e.getProductPrice() * discountedPercentage);
      }
      e.setDiscountedPrice(discountedPrice);

      JSONObject jobj = new JSONObject();

      jobj.put("productPrice", e.getProductPrice());
      jobj.put("productName", e.getProductName());
      jobj.put("productImage", e.getProductImage());
      jobj.put("eventTitle", e.getEventTitle());
      jobj.put("productNo", e.getProductNo());
      jobj.put("discountedPrice", discountedPrice);

      jobj.put("eventStartDate", eventStartDate);
      jobj.put("eventEndDate", eventEndDate);

      jarr.put(jobj);
    }

    return jarr.toString();
  }

  // 이벤트 번호에 맞는 상품 이름 검색 조회
  @RequestMapping("productSearch.ens")
  public String productNameSearch(
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "eventNo", required = false) Integer eventNo,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "productNameSearch", required = false) String productNameSearch,
      @RequestParam(value = "productSearchPage", required = false) Integer currentPage,
      Model model) {

    if (search == null) {
      search = "";
    }

    if (productNameSearch == null) {
      productNameSearch = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    HashMap<String, Object> data = new HashMap<>();
    data.put("productNameSearch", productNameSearch);
    data.put("eventNo", eventNo);

    int listCount = eService.selectSearchEventListCountDetail(data);
    PageInfo productPi = Pagination.getPageInfo(currentPage, listCount, 6);

    int ep = Pagination.getPageInfo(currentPage, listCount, 6).getEndPage();

    pagingArrange(productPi, currentPage, ep);

    ArrayList<MarketEventNews> eList = eService.selectSearchEventListDetail(productPi, data);

    String eventTitle = null;
    String eventContent = null;
    String eventStartDate = null;
    String eventEndDate = null;
    int discountedPrice = 0;

    for (MarketEventNews e : eList) {

      eventTitle = e.getEventTitle();
      eventContent = e.getEventContent();
      eventStartDate = String.format("%1$tY-%1$tm-%1$td", e.getEventStartDate());
      eventEndDate = String.format("%1$tY-%1$tm-%1$td", e.getEventEndDate());

      if (e.getEventCode() > 100) {
        discountedPrice = e.getProductPrice() - e.getEventCode();
      } else {
        float discountedPercentage = (100 - e.getEventCode()) / 100.0f;
        discountedPrice = (int) (e.getProductPrice() * discountedPercentage);
      }
      e.setDiscountedPrice(discountedPrice);
    }

    // 이벤트 글 정보
    if (!eList.isEmpty() || eList.isEmpty()) {
      model.addAttribute("eList", eList);
      model.addAttribute("productPi", productPi);
      model.addAttribute("search", search);
      model.addAttribute("page", page);
      model.addAttribute("eventNo", eventNo);
      model.addAttribute("productNameSearch", productNameSearch);
      model.addAttribute("productSearchPage", currentPage);
      model.addAttribute("eventTitle", eventTitle);
      model.addAttribute("eventContent", eventContent);
      model.addAttribute("eventStartDate", eventStartDate);
      model.addAttribute("eventEndDate", eventEndDate);

    }

    return "eventNewsDetail";
  }

  // 이벤트 날짜순 정렬 버튼 누르면 리스트 반영 됨.
  @GetMapping(value = "selectEventNewsByDate.ens")
  @ResponseBody
  public ResponseEntity<String> selectEventNewsByDate(
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "eventNoOnly", required = false) Integer eventNoOnly,
      @RequestParam(value = "page", required = false) Integer currentPage) {

    if (search == null) {
      search = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    int listCount = eService.selectEventNewsByDate(search);
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

    ArrayList<MarketEventNews> eList = eService.selectEventNewsListByDate(pi, search);

    Context context = new Context();

    context.setVariable("eList", eList);

    return ResponseEntity.ok(templateEngine.process("eventNewsProductList", context));
  }

  // 이벤트 카테고리 버튼 누르면 같이 반영 되는 페이징 (productPi).
  @GetMapping(value = "selectEventNewsByDatePaging.ens")
  @ResponseBody
  public ResponseEntity<String> selectEventNewsByDatePaging(
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "eventNoOnly", required = false) Integer eventNoOnly,
      @RequestParam(value = "page", required = false) Integer currentPage) {

    if (search == null) {
      search = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    int listCount = eService.selectEventNewsByDate(search);
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

    Context context = new Context();

    context.setVariable("currentPage", pi.getCurrentPage());
    context.setVariable("startPage", pi.getStartPage());
    context.setVariable("endPage", pi.getEndPage());
    context.setVariable("maxPage", pi.getMaxPage());

    return ResponseEntity.ok(templateEngine.process("eventNewsProductPaging", context));
  }

  // 페이징 처리 메소드
  public void pagingArrange(PageInfo pi, Integer currentPage, int ep) {
    if (pi.getEndPage() < currentPage) {
      pi.setCurrentPage(pi.getEndPage());
      pi.setStartPage(1);
    }

    if (pi.getStartPage() > currentPage) {
      pi.setCurrentPage(1);
      pi.setStartPage(1);
      pi.setEndPage(ep);
    }

    if (pi.getEndPage() == 0 || pi.getCurrentPage() == 0) {
      pi.setEndPage(1);
      pi.setCurrentPage(1);
    }
  }
}
