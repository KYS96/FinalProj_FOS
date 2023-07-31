package com.kh.slumber.member.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.exception.MemberException;
import com.kh.slumber.member.model.service.MemberService;
import com.kh.slumber.member.model.vo.KakaoProfile;
import com.kh.slumber.member.model.vo.Member;
import com.kh.slumber.member.model.vo.MemberBoard;
import com.kh.slumber.member.model.vo.MemberContact;
import com.kh.slumber.member.model.vo.MemberCoupon;
import com.kh.slumber.member.model.vo.MemberOrder;
import com.kh.slumber.member.model.vo.MemberReply;
import com.kh.slumber.member.model.vo.MemberReview;
import com.kh.slumber.member.model.vo.OAuthToken;

import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

@SessionAttributes("loginUser")
@Controller
public class MemberController {

    @Autowired
    private MemberService mService;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private TemplateEngine templateEngine;

    private DefaultMessageService msgService;

    public MemberController() {
        this.msgService = NurigoApp.INSTANCE.initialize("NCSJ80A5OGPLRWAK", "NQ1LL80AWXICQOFUAMHTYXHG2SYVSJ0O", "https://api.coolsms.co.kr");
    }

    @RequestMapping("memberAlarm.me")
    @ResponseBody
    public ResponseEntity<String> memberAlarm(HttpSession session) {
        String memberNo = null;

        int num = 0;

        ArrayList<MemberCoupon> coupons = new ArrayList<>();
        ArrayList<MemberReply> replies = new ArrayList<>();

        if(session.getAttribute("loginUser") != null) {
            memberNo = ((Member)(session.getAttribute("loginUser"))).getMemberNo();

            coupons = mService.alarmCoupon(memberNo);

            ArrayList<MemberBoard> boards = mService.selectBoard(memberNo);

            for(MemberBoard board : boards) {
                replies.addAll(mService.alarmReply(board.getBoardNo()));
            }

            Iterator<MemberReply> iterator = replies.iterator();

            while(iterator.hasNext()) {
                if(iterator.next().getMemberNo().equals(memberNo)) {
                    iterator.remove();
                }
            }

            num += coupons.size();
            num += replies.size();
        }

        Context context = new Context();

        context.setVariable("coupon", coupons);
        context.setVariable("reply", replies);
        context.setVariable("num", num);
        context.setVariable("memberNo", memberNo);

        return ResponseEntity.ok(templateEngine.process("list/myPageAlarm", context));
    }

    @RequestMapping("checkAlarm.me")
    @ResponseBody
    public void checkAlarm(String replyNo) {
        mService.checkAlarm(replyNo);
    }

    @RequestMapping("deleteAlarm.me")
    @ResponseBody
    public void deleteAlarm(String memberNo) {
        mService.deleteCouponAlarm(memberNo);

        ArrayList<MemberBoard> boards = mService.selectBoard(memberNo);

        ArrayList<MemberReply> replies = new ArrayList<>();

        for(MemberBoard board : boards) {
            replies.addAll(mService.alarmReply(board.getBoardNo()));
        }

        Iterator<MemberReply> iterator = replies.iterator();

        while(iterator.hasNext()) {
            if(iterator.next().getMemberNo().equals(memberNo)) {
                iterator.remove();
            }
        }
        
        for(MemberReply reply : replies) {
            mService.deleteReplyAlarm(reply.getReplyNo());
        }
    }

    @RequestMapping("memberPaging.me")
    @ResponseBody
    public ResponseEntity<String> memberPaging(@RequestParam(value = "page", required = false) Integer page, String func, String type, int boardLimit, HttpSession session) {
        String memberNo = ((Member)(session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount(type, memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, boardLimit);

        Context context = new Context();

        context.setVariable("pi", pi);
        context.setVariable("func", func);

        return ResponseEntity.ok(templateEngine.process("common/mypagePaging", context));
    }

    @RequestMapping("myPage.me")
    public String myPage(Model model, HttpSession session) {
        String memberNo = ((Member)(session.getAttribute("loginUser"))).getMemberNo();

        ArrayList<MemberCoupon> currentCouponList = mService.selectCurrentCoupon(memberNo);
        ArrayList<MemberCoupon> endCouponList = mService.selectEndCoupon(memberNo);

        for(MemberCoupon c : currentCouponList) {
            c.setCouponEndDate(c.getCouponEndDate().split(" ")[0]);
        }

        for(MemberCoupon c : endCouponList) {
            c.setCouponEndDate(c.getCouponEndDate().split(" ")[0]);
        }

        model.addAttribute("cCList", currentCouponList);
        model.addAttribute("eCList", endCouponList);

        ArrayList<MemberOrder> oList = mService.selectOrder(memberNo);

        model.addAttribute("oList", oList);

        ArrayList<MemberReview> rList = mService.selectReview(memberNo);

        model.addAttribute("rList", rList);

        ArrayList<MemberContact> cList = mService.selectContact(memberNo);
        ArrayList<MemberContact> aList = mService.selectAnswer(memberNo);

        model.addAttribute("cList", cList);
        model.addAttribute("aList", aList);

        ArrayList<MemberBoard> bBList = mService.selectBoard(memberNo);
        ArrayList<MemberReply> bRList = mService.selectReply(memberNo);

        for (MemberBoard b : bBList) {
            b.setReplyCount(mService.countReply(b.getBoardNo()));
        }

        model.addAttribute("bBList", bBList);
        model.addAttribute("bRList", bRList);

        return "myPage";
    }

    @RequestMapping("myInfo.me")
    public String myInfo() {
        return "myInfo";
    }

    @RequestMapping("updateMember.me")
    public String updateMember(@ModelAttribute Member m, String memberPasswordCheck, Model model) {
        int result = 0;

        if(m.getMemberPassword().isEmpty()) {
            result = mService.updateMember(m);
        } else {
            if(m.getMemberPassword().equals(memberPasswordCheck)) {
                m.setMemberPassword(bcrypt.encode(memberPasswordCheck));

                result = mService.updateMember(m);
            } else {
                System.out.println("비밀번호 변경 실패");
            }
        }

        if(result > 0) {
            model.addAttribute("loginUser", mService.login(m));
            return "redirect:myInfo.me";
        }

        throw new MemberException("비밀번호 변경 실패");
    }

    @RequestMapping("myCoupon.me")
    public String myCoupon(HttpSession session, Model model) {
        String memberNo = ((Member)session.getAttribute("loginUser")).getMemberNo();

        ArrayList<MemberCoupon> cList = mService.selectCurrentCoupon(memberNo);
        ArrayList<MemberCoupon> eList = mService.selectEndCoupon(memberNo);
        ArrayList<MemberCoupon> uList = mService.selectUseCoupon(memberNo);

        for(MemberCoupon c : cList) {
            c.setCouponEndDate(c.getCouponEndDate().split(" ")[0]);
        }

        for(MemberCoupon c : eList) {
            c.setCouponEndDate(c.getCouponEndDate().split(" ")[0]);
        }

        for(MemberCoupon c : uList) {
            c.setCouponEndDate(c.getCouponEndDate().split(" ")[0]);
        }

        model.addAttribute("cList", cList);
        model.addAttribute("eList", eList);
        model.addAttribute("uList", uList);

        return "myCoupon";
    }

    @RequestMapping("couponDetail.me")
    @ResponseBody
    public ResponseEntity<String> couponDetail(String couponIssuanceNo) {
        MemberCoupon mc = mService.couponDetail(couponIssuanceNo);

        Context context = new Context();

        mc.setCouponEndDate(mc.getCouponEndDate().split(" ")[0]);

        context.setVariable("mc", mc);

        return ResponseEntity.ok(templateEngine.process("list/myCouponDetail", context));
    }

    @RequestMapping("myBoard.me")
    public String myBoard() {
        return "myBoard";
    }

    @RequestMapping("memberBoardList.me")
    @ResponseBody
    public ResponseEntity<String> memberBoardList(@RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("board", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<MemberBoard> list = mService.selectBoardList(pi, memberNo);

        for (MemberBoard m : list) {
            m.setReplyCount(mService.countReply(m.getBoardNo()));
        }

        Context context = new Context();

        context.setVariable("list", list);

        return ResponseEntity.ok(templateEngine.process("list/myBoardList", context));
    }

    @RequestMapping("memberReplyList.me")
    @ResponseBody
    public ResponseEntity<String> memberReplyList(@RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("board", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<MemberReply> list = mService.selectReplyList(pi, memberNo);

        Context context = new Context();

        context.setVariable("list", list);

        return ResponseEntity.ok(templateEngine.process("list/myReplyList", context));
    }

    @RequestMapping("deleteBoard.me")
    @ResponseBody
    public String deleteBoard(String boardNo) {
        int result = mService.deleteBoard(boardNo);

        if (result > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("deleteReply.me")
    @ResponseBody
    public String deleteReply(String replyNo) {
        int result = mService.deleteReply(replyNo);

        if (result > 0) {
            return "myBoard";
        } else {
            return "fail";
        }
    }

    @RequestMapping("myContact.me")
    public String myContact() {
        return "myContact";
    }

    @RequestMapping("memberContactList.me")
    @ResponseBody
    public ResponseEntity<String> memberContactList(@RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("contact", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);

        ArrayList<MemberContact> list = mService.selectContactList(pi, memberNo);

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("currentPage", pi.getCurrentPage());

        return ResponseEntity.ok(templateEngine.process("list/myContactList", context));
    }

    @RequestMapping("memberAnswerList.me")
    @ResponseBody
    public ResponseEntity<String> memberAnswerList(@RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("answer", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);

        ArrayList<MemberContact> list = mService.selectAnswerList(pi, memberNo);

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("currentPage", pi.getCurrentPage());

        return ResponseEntity.ok(templateEngine.process("list/myAnswerList", context));
    }

    @RequestMapping("deleteContact.me")
    @ResponseBody
    public String deleteContact(String questionNo) {
        int result = mService.deleteContact(questionNo);

        if (result > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("contactDetail.me")
    @ResponseBody
    public ResponseEntity<String> contactDetail(String questionNo) {
        MemberContact mc = mService.contactDetail(questionNo);

        Context context = new Context();

        context.setVariable("mc", mc);

        return ResponseEntity.ok(templateEngine.process("list/myContactDetail", context));
    }

    @RequestMapping("myReview.me")
    public String myReview() {
        return "myReview";
    }

    @RequestMapping("memberReviewList.me")
    @ResponseBody
    public ResponseEntity<String> memberReviewList(@RequestParam(value = "page", required = false) Integer page, HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("review", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<MemberReview> list = mService.selectReviewList(pi, memberNo);

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("currentPage", pi.getCurrentPage());

        return ResponseEntity.ok(templateEngine.process("list/myReviewList", context));
    }

    @RequestMapping("deleteReview.me")
    @ResponseBody
    public String deleteReview(String reviewNo) {
        int result = mService.deleteReview(reviewNo);

        if (result > 0) {
            return "success";
        }

        throw new MemberException("비밀번호 변경 실패");
    }

    @RequestMapping("reviewUpdatePopup.me")
    public String findReview(String reviewNo, Model model) {
        Decoder decoder = Base64.getDecoder();
        byte[] byteArr = decoder.decode(reviewNo);
        String decode = new String(byteArr);
        MemberReview mr = mService.findReview(decode);

        model.addAttribute("mr", mr);

        return "reviewUpdatePopup";
    }

    @RequestMapping("selectReviewImage.me")
    @ResponseBody
    public String[] selectReviewImage(String reviewNo) {
        String[] images = mService.selectReviewImage(reviewNo);

        return images;
    }

    @RequestMapping("updateReview.me")
    @ResponseBody
    public String updateReview(@RequestParam(value = "insertImages", required = false) String[] insertImages,
            @RequestParam(value = "deleteImages", required = false) String[] deleteImages,
            String reviewNo, String reviewContent, String reviewPoint, String productNo, HttpSession session) {

        MemberReview mr = new MemberReview();

        mr.setReviewNo(reviewNo);
        mr.setReviewContent(reviewContent);
        mr.setReviewPoint(reviewPoint);

        int result = 0;

        if (insertImages != null) {
            for (String insertImage : insertImages) {
                String name = insertImage.split("/")[insertImage.split("/").length - 1];

                String originName = name.split("-")[name.split("-").length - 1];
                String reName = name.replace("-" + name, "");

                HashMap<String, String> hs = new HashMap<>();
                hs.put("attmUrl", insertImage);
                hs.put("reviewNo", reviewNo);
                hs.put("attmName", originName);
                hs.put("attmSaveName", reName);
                hs.put("productNo", productNo);

                result += mService.insertReviewImage(hs);
            }
        }

        if (deleteImages != null) {
            for (String deleteImage : deleteImages) {
                HashMap<String, String> hs = new HashMap<>();
                hs.put("attmUrl", deleteImage);
                hs.put("reviewNo", reviewNo);
                result += mService.deleteReviewImage(hs);
            }
        }

        result = mService.updateReview(mr);

        if (result > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("myOrder.me")
    public String myOrder() {
        return "myOrder";
    }

    @RequestMapping("memberOrderList.me")
    @ResponseBody
    public ResponseEntity<String> memberOrderList(@RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        String memberNo = ((Member) (session.getAttribute("loginUser"))).getMemberNo();

        int currentPage = 1;

        if (page != null) {
            currentPage = page;
        }

        int listCount = mService.getListCount("order", memberNo);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<MemberOrder> list = mService.selectOrderList(pi, memberNo);

        Context context = new Context();

        context.setVariable("list", list);

        return ResponseEntity.ok(templateEngine.process("list/myOrderList", context));
    }

    @RequestMapping("orderDetail.me")
    @ResponseBody
    public ResponseEntity<String> orderDetail(String cartNo, String orderNo) {
        HashMap<String, String> hm = new HashMap<>();

        if (cartNo == "") {
            hm.put("value", orderNo);
            hm.put("type", "ORDER_NO");
        } else {
            hm.put("value", cartNo);
            hm.put("type", "CART_NO");
        }

        ArrayList<MemberOrder> list = mService.orderDetail(hm);

        double totalPrice = 0;

        for(MemberOrder mo : list) {
            if(mo.getEventCode() >= 100) {
                totalPrice += (mo.getProductPrice() - mo.getEventCode()) * mo.getOrderAmount();
            } else {
                totalPrice += (mo.getProductPrice() * (1 - (mo.getEventCode() / 100))) * mo.getOrderAmount();
            }
        }

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("totalPrice", totalPrice);

        return ResponseEntity.ok(templateEngine.process("list/myOrderDetail", context));
    }

    @PostMapping("enrollMember.me")
    public String enrollMember(@ModelAttribute Member m) {
        String encPwd = bcrypt.encode(m.getMemberPassword());
        m.setMemberPassword(encPwd);
        
        int result1 = mService.enrollMember(m);
        
        ArrayList<MemberCoupon> couponList = mService.findEnrollCoupon();
        
        String couponEndDate = null;
        
        for(MemberCoupon coupon : couponList){
            System.out.println("쿠폰:" + coupon);

            if (coupon.getCouponPeriodType().equals("obtain")) {
                couponEndDate = "SYSDATE + " + coupon.getCouponPeriod();
            }
            if (coupon.getCouponPeriodType().equals("close")) {
                couponEndDate = "TO_DATE('" + coupon.getCouponCloseDate().split(" ")[0] + "', 'YY-MM-DD') +"
                        + coupon.getCouponPeriod();
            }
            if (coupon.getCouponPeriodType().equals("same")) {
                couponEndDate = "TO_DATE('" + coupon.getCouponCloseDate().split(" ")[0] + "', 'YY-MM-DD')";
            }

            coupon.setCouponEndDate(couponEndDate);
            
            mService.enrollCoupon(coupon);
        }


        if (result1 > 0 ) {
            return "redirect:home.do";
        } else {
            throw new MemberException("회원가입 실패");
        }
    }

    @PostMapping("findId.me")
    public String findId(@ModelAttribute Member m, Model model) {

        ArrayList<Member> memberList = mService.findId(m);
        System.out.println(memberList);

        List<String> memberIdList = new ArrayList<>();
        for (Member member : memberList) {
            String memberId = member.getMemberId();
            memberIdList.add(memberId);
        }

        System.out.println(memberIdList);
        if (memberList.isEmpty()) {
            return "findIdFail";
        }

        if (memberIdList != null) {
            model.addAttribute("memberName", m.getMemberName());
            model.addAttribute("memberId", memberIdList);
            return "findIdResult";
        } else {
            throw new MemberException("회원가입 실패");
        }
    }

    @PostMapping("findPassword.me")
    public String findPassword(@ModelAttribute Member m, Model model) {

        Member member = mService.findPassword(m);
        if (member == null) {
            return "findPasswordFail";
        }
        if (m.getMemberId() != null) {
            model.addAttribute("memberId", m.getMemberId());
            return "findPasswordResult";
        } else {
            throw new MemberException("비밀번호 변경 실패");
        }
    }

    @PostMapping("changePassword.me")
    public String changePassword(@ModelAttribute Member m, HttpSession session, Model model) {
        System.out.println(m);

        String encPwd = bcrypt.encode(m.getMemberPassword());
        m.setMemberPassword(encPwd);

        int result = mService.changePassword(m);

        if (result > 0) {
            return "login";
        } else {
            throw new MemberException("비밀번호 변경 실패");
        }
    }

    @RequestMapping("loginView.me")
    public String loginView() {
        return "login";
    }

    @RequestMapping("checkId.me")
    public void checkId(String memberId, HttpSession session, PrintWriter out) {
        int count = mService.checkId(memberId);
        String result = count == 0 ? "yes" : "no";
        out.print(result);
    }

    @RequestMapping("checkNickname.me")
    public void checkNickname(String memberNickname, HttpSession session, PrintWriter out) {
        int count = mService.checkNickname(memberNickname);

        String result = count == 0 ? "yes" : "no";
        out.print(result);
    }

    @RequestMapping("checkDuplication.me")
    public void checkDuplication(String memberNickname, HttpSession session, PrintWriter out) {
        Member m = new Member();
        m.setMemberNickname(memberNickname);
        m.setMemberId(((Member) session.getAttribute("loginUser")).getMemberId());

        int count = mService.checkDuplication(memberNickname);
        int isMyNickname = mService.checkMyNickname(m);

        String result = null;

        if (count == 0 && isMyNickname == 0) {
            result = "Y";
        } else if (isMyNickname == 1) {
            result = "M";
        } else {
            result = "N";
        }

        out.print(result);
    }

    @RequestMapping("sendCode.me")
    @ResponseBody
    public String sendCode(String memberPhone, Model model) {
        Message msg = new Message();
        Random r = new Random();
        String numStr = "";

        for (int i = 0; i < 4; i++) {
            String ran = Integer.toString(r.nextInt(10));
            numStr += ran;
        }

        msg.setFrom("010-5247-1664");
        msg.setTo(memberPhone);
        msg.setText("[수면의숲]\n인증번호는 [" + numStr + "] 입니다.");

        this.msgService.sendOne(new SingleMessageSendingRequest(msg));

        return numStr;
    }

    @RequestMapping(value="login.me", method=RequestMethod.POST)
	public String login(Member m, Model model) {

		Member loginUser = mService.login(m); 
        if(loginUser==null){
            // 실패 시 모달을 표시하는 JavaScript 코드를 추가
            model.addAttribute("errorMessage", "비밀 번호를 다시 확인해주세요.");
            return "loginFail"; // 모달을 표시할 페이지로 이동
        }

        if (bcrypt.matches(m.getMemberPassword(), loginUser.getMemberPassword())) {
            model.addAttribute("loginUser", loginUser);

            return "redirect:home.do";

        } else {
            // 실패 시 모달을 표시하는 JavaScript 코드를 추가
            model.addAttribute("errorMessage", "비밀 번호를 다시 확인해주세요.");
            return "loginFail"; // 모달을 표시할 페이지로 이동
        }
    }

    @RequestMapping(value="adminLogin.me", method=RequestMethod.POST)
	public String login(Admin a, Model model) {
		Member loginUser = new Member();
		Admin loginAdmin = mService.adminLogin(a);
        
        loginUser.setMemberNo(loginAdmin.getAdminNo());
        loginUser.setMemberName(loginAdmin.getAdminName());
        loginUser.setMemberNickname("관리자");

        if(loginUser.getMemberName() == null){
            // 실패 시 모달을 표시하는 JavaScript 코드를 추가
            model.addAttribute("errorMessage", "비밀 번호를 다시 확인해주세요.");
            return "loginFail"; // 모달을 표시할 페이지로 이동
        }

        if (bcrypt.matches(a.getAdminPwd(), loginAdmin.getAdminPwd())) {
            model.addAttribute("loginUser", loginUser);

            return "redirect:statisticMain.admin";

        } else {
            // 실패 시 모달을 표시하는 JavaScript 코드를 추가
            model.addAttribute("errorMessage", "비밀 번호를 다시 확인해주세요.");
            return "loginFail"; // 모달을 표시할 페이지로 이동
        }
    }

    @RequestMapping("enroll.me")
    public String enroll() {
        return "enroll";
    }

    @RequestMapping("findIdView.me")
    public String findIdView() {
        return "findIdView";
    }

    @RequestMapping("findPasswordView.me")
    public String findPassword() {
        return "findPasswordView";
    }

    @RequestMapping("logout.me")
    public String logout(SessionStatus session) {
        session.setComplete();
        return "redirect:home.do";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code, RedirectAttributes redirectAttributes){
        //post방식으로 key=value 데이터를 요청 ( 카카오 쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "45a183927ce0759f809b79717ae3c600");
        params.add("redirect_uri", "http://127.0.0.1:8081/auth/kakao/callback");
        params.add("code", code);

        // httpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, params);

        // Http 요청하기 -post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        // Gson, Json simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        // System.out.println("카카오 엑세스 토큰 : "+ oauthToken.getAccess_token());
        
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // httpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        // Http 요청하기 -post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class);
        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = new KakaoProfile();

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        
        // System.out.println("카카오 아이디(번호): "+kakaoProfile.getId());
        // System.out.println("카카오 email: "+kakaoProfile.getKakao_account().getEmail());
        
        // System.out.println("수면의숲 서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        // System.out.println("수면의숲 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        // System.out.println("수면의숲 닉네임 : " + kakaoProfile.properties.getNickname());
        
        UUID garbagePassword = UUID.randomUUID();
        // System.out.println("수면의숲 패스워드 : " + garbagePassword );
        
        Member m = new Member();
        String password = garbagePassword.toString();
        String kakaoId = String.valueOf(kakaoProfile.getId());

        m.setMemberId("kakao@"+kakaoId);
        m.setMemberNickname(kakaoProfile.properties.getNickname());
        m.setMemberPassword(password);

        // System.out.println("체크전 멤버아이디: "+m.getMemberId());

        // 가입자인지 체크
        Member kakaoMember = mService.kakaoFindId(m.getMemberId());

        // System.out.println("체크후 카카오멤버: " +kakaoMember);

        redirectAttributes.addFlashAttribute("member", m);

        if(kakaoMember != null){

            return "redirect:/kakaoLogin.me";
        }

        return "redirect:/kakaoEnroll.me";
    }

    @GetMapping("/kakaoEnroll.me")
    public String enrollPage(@ModelAttribute("member") Member m) {

        return "kakaoEnroll";
    }

    @GetMapping(value = "/kakaoLogin.me")
    public String kakaoLogin(@ModelAttribute("member") Member m, Model model) {
        System.out.println(m);
        Member loginUser = mService.login(m);
        System.out.println(loginUser);
        if (loginUser == null) {
            // 실패 시 모달을 표시하는 JavaScript 코드를 추가
            model.addAttribute("errorMessage", "비밀 번호를 다시 확인해주세요.");
            return "loginFail"; // 모달을 표시할 페이지로 이동
        }
        model.addAttribute("loginUser", loginUser);
        return "redirect:home.do";
    }
}
