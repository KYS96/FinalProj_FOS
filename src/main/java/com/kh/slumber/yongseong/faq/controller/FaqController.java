package com.kh.slumber.yongseong.faq.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.faq.model.service.FaqService;
import com.kh.slumber.yongseong.faq.model.vo.MarketFAQ;

@SessionAttributes("loginUser")
@Controller
public class FaqController {

  @Autowired
  private FaqService fService;

  @GetMapping("FAQ.faq")
  public String faq(@RequestParam(value = "faqSearchBar", required = false) String faqSearchBar,
      @RequestParam(value = "page", required = false) Integer currentPage,
      Model model) {

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    int listCount = fService.selectFaqSearchListCount(faqSearchBar);
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

    int ep = Pagination.getPageInfo(currentPage, listCount, 10).getEndPage();

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

    // FAQ 조회 : 리스트 불러옴
    ArrayList<MarketFAQ> list = fService.selectFaqSearchList(pi, faqSearchBar);

    // accordion에 붙을 알파벳들
    ArrayList<String> alphabetList = new ArrayList<>();

    char alphabet = 'a';
    for (int i = 0; i < list.size(); i++) {
      alphabetList.add(String.valueOf(alphabet));
      alphabet++;
    }

    System.out.println(list);

    if (!list.isEmpty()) {
      model.addAttribute("list", list);
      model.addAttribute("pi", pi);
      model.addAttribute("alphabetList", alphabetList);
      model.addAttribute("searchContent", faqSearchBar);

      return "FAQ";
    } else {
      model.addAttribute("list", list);
      model.addAttribute("searchContent", faqSearchBar);

      return "FAQ";
    }
  }

  @RequestMapping("faqWrite.faq")
  public String faqWrite() {
    // FAQ 작성 화면으로 넘어가기
    return "faqWrite";
  }

  @RequestMapping("faqInsert.faq")
  public String faqInsert(@ModelAttribute MarketFAQ faq) {
    int result = fService.faqInsert(faq);
    if (result > 0) {
      return "redirect:FAQ.faq";
    } else {
      return null;
    }
  }

  @RequestMapping(value = "faqDelete.faq", produces = "application/json; charset=UTF-8")
  @ResponseBody
  // HttpSession 관리자 번호 받기
  public String faqDelete(String[] faqNoArr, Integer currentPage) {

    currentPage = 1;

    // FAQ 리스트 총 개수
    int listCount = fService.selectFaqListCount();

    // 페이징 처리
    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

    // FAQ 조회 : 리스트 불러옴
    ArrayList<MarketFAQ> list = fService.selectFaqList(pi);

    for (String fNo : faqNoArr) {
      fService.faqDelete(fNo);
    }

    JSONArray jarr = new JSONArray();

    char alphabet = 'a';
    for (MarketFAQ l : list) {
      JSONObject jobj = new JSONObject();

      jobj.put("faqNo", l.getFaqNo());
      jobj.put("faqTitle", l.getFaqTitle());
      jobj.put("faqContent", l.getFaqContent());
      jobj.put("alphabet", String.valueOf(alphabet));
      jobj.put("page", currentPage);

      jarr.put(jobj);

      alphabet++;
    }

    return jarr.toString();
  }

  // FAQ 수정
  @RequestMapping("faqUpdate.faq")
  public String faqUpdate(int faqNo, Model model) {

    MarketFAQ faqInfo = fService.faqUpdate(faqNo);

    model.addAttribute("faqInfo", faqInfo);

    return "FAQUpdate";
  }

  // FAQ 수정 진행

  @RequestMapping("faqUpdateConfirm.faq")
  public String faqUpdateConfirm(int faqNo, String faqTitle, String faqContent) {

    HashMap<Object, Object> data = new HashMap<>();

    data.put("faqNo", faqNo);
    data.put("faqTitle", faqTitle);
    data.put("faqContent", faqContent);

    int result = fService.faqUpdateConfirm(data);

    if (result > 0) {
      return "redirect:FAQ.faq";
    } else {
      return null;
    }
  }

}
