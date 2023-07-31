package com.kh.slumber.yongseong.notice.controller;

import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.yongseong.notice.model.service.NotificationService;
import com.kh.slumber.yongseong.notice.model.vo.MarketNotice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SessionAttributes("loginUser")
@Controller
public class NoticeController {

  @Autowired
  private NotificationService nService;

  // 공지사항 메인 화면
  @RequestMapping("notice.no")
  public String notice(@RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "page", required = false) Integer currentPage,
      Model model) {

    // 공지사항 탭 들어오자마자 전체 검색 결과 확인
    if (category == null || category == "") {
      category = "all";
    }

    if (search == null) {
      search = "";
    }

    if (currentPage == null || currentPage < 0) {
      currentPage = 1;
    }

    Properties prop = new Properties();
    prop.setProperty("category", category);
    prop.setProperty("search", search);

    int listCount = nService.selectNoticeSearchListCount(prop);

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

    ArrayList<MarketNotice> nList = nService.selectNoticeSearchList(pi, prop);

    if (!nList.isEmpty()) {
      model.addAttribute("nList", nList);
      model.addAttribute("category", category);
      model.addAttribute("search", search);
      model.addAttribute("pi", pi);
      model.addAttribute("page", currentPage);

      return "notification";
    } else {
      model.addAttribute("nList", nList);
      model.addAttribute("category", category);
      model.addAttribute("search", search);

      return "notification";
    }
  }

  // 공지사항 상세 페이지로 넘어가기 //조회수에 관한 쿠키 존재
  @RequestMapping("noticeDetail.no")
  public String noticeDetail(@RequestParam("empBoardNo") int empBoardNo,
      Model model, HttpServletRequest request, HttpServletResponse response) {

    // 상세 정보 가져오기
    // 쿠키 받아오기
    MarketNotice n = nService.selectDetailNoticePage(empBoardNo);

    Cookie[] cookies = request.getCookies();

    Cookie viewCookie = null;

    if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
        if (cookies[i].getName().equals("cookie" + empBoardNo)) {
          viewCookie = cookies[i]; // 쿠키 생성됨
        }
      }
    }

    if (n != null) {
      if (viewCookie == null) {
        Cookie newCookie = new Cookie("cookie" + empBoardNo, "|" + empBoardNo + "|");
        response.addCookie(newCookie);
        nService.noticeAddCount(empBoardNo);
        n.setEmployeeBoardViews(n.getEmployeeBoardViews() + 1);
      }
      model.addAttribute("n", n);
      model.addAttribute("employeeBoardNo", n.getEmployeeBoardNo());
      return "notificationDetail";
    } else {
      return null;
    }
  }

  // 공지사항 작성 페이지로 이동
  @RequestMapping("noticeWrite.no")
  public String noticeWrite() {
    return "notificationWrite";
  }

  // 공지사항 작성 ( 첨부파일 및 글 존재 )
  @RequestMapping("noticeInsert.no")
  public String noticeInsert(@RequestParam("employeeBoardTitle") String employeeBoardTitle,
      @RequestParam("employeeBoardContent") String employeeBoardContent) {

    MarketNotice mn = new MarketNotice();
    mn.setEmployeeBoardTitle(employeeBoardTitle);
    mn.setEmployeeBoardContent(employeeBoardContent);

    int result = nService.noticeInsert(mn);

    if (result > 0) {
      return "redirect:notice.no";
    } else {
      return null;
    }
  }

  // 공지사항 수정 팝업 띄우기
  @RequestMapping("goNoticeUpdatePage.no")
  public String goNoticeUpdatePage(@RequestParam("employeeBoardNo") int employeeBoardNo, Model mode) {
    // 정보 가져오기.
    MarketNotice n = nService.selectDetailNoticePage(employeeBoardNo);

    System.out.println(n);

    if (n != null) {
      mode.addAttribute("n", n);
      return "notificationUpdate";
    } else {
      return null;
    }
  }

  // 공지사항 수정하기(팝업에서 수정함)
  @RequestMapping("notificationUpdateConfirm.no")
  public String noticeUpdatePopupConfirm(@RequestParam("employeeBoardNo") int employeeBoardNo,
      @RequestParam("employeeBoardTitle") String employeeBoardTitle,
      @RequestParam("employeeBoardContent") String employeeBoardContent,
      RedirectAttributes ra) {
    // int employeeNo=(Employee)(session.getAttribute("loginUser")).getEmployeeNo();
    // 이 번호를 DB에 넘기기 (공지사항 수정에 관리자 번호 들어가야 됨)

    MarketNotice mn = new MarketNotice();

    // mn.setEmployeeBoardNo(employeeNo);
    mn.setEmployeeBoardNo(employeeBoardNo);
    mn.setEmployeeBoardTitle(employeeBoardTitle);
    mn.setEmployeeBoardContent(employeeBoardContent);

    int result = nService.noticeUpdatePopupConfirm(mn);

    if (result > 0) {
      ra.addAttribute("empBoardNo", employeeBoardNo);

      return "redirect:noticeDetail.no";
    } else {
      return null;
    }
  }

  // 공지사항 삭제(상태 값 변경)
  @RequestMapping("noticeDelete.no")
  public String noticeDelete(@RequestParam("employeeBoardNo") int employeeBoardNo) {

    int result = nService.noticeDelete(employeeBoardNo);

    if (result > 0) {
      return "redirect:notice.no";
    } else {
      return null;
    }
  }

}
