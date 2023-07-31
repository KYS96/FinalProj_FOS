package com.kh.slumber.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.vo.Member;
import com.kh.slumber.member.model.vo.MemberBoard;
import com.kh.slumber.member.model.vo.MemberContact;
import com.kh.slumber.member.model.vo.MemberCoupon;
import com.kh.slumber.member.model.vo.MemberOrder;
import com.kh.slumber.member.model.vo.MemberReply;
import com.kh.slumber.member.model.vo.MemberReview;


public interface MemberService {

    Member login(Member m);

    int enrollMember(Member m);

    // 마이페이지
    int checkDuplication(String memberNickname);

    int checkMyNickname(Member m);

    int updateMember(Member m);

    // 알람
    ArrayList<MemberCoupon> alarmCoupon(String memberNo);

    int deleteCouponAlarm(String memberNo);

    ArrayList<MemberReply> alarmReply(String boardNo);

    int deleteReplyAlarm(String replyNo);

    int checkAlarm(String replyNo);

    // 페이징
    int getListCount(String type, String memberNo);

    RowBounds pagination(PageInfo pi);

    // 쿠폰
    ArrayList<MemberCoupon> selectCurrentCoupon(String memberNo);

    ArrayList<MemberCoupon> selectUseCoupon(String memberId);

    ArrayList<MemberCoupon> selectEndCoupon(String memberNo);

    MemberCoupon couponDetail(String couponIssuanceNo);

    // 게시판, 댓글
    ArrayList<MemberBoard> selectBoard(String memberNo);

    ArrayList<MemberBoard> selectBoardList(PageInfo boardPi, String memberNo);

    int deleteBoard(String boardNo);

    ArrayList<MemberReply> selectReply(String memberNo);

    ArrayList<MemberReply> selectReplyList(PageInfo replyPi, String memberNo);

    int deleteReply(String replyNo);

    int countReply(String boardNo);

    // 문의
    ArrayList<MemberContact> selectContact(String memberNo);

    ArrayList<MemberContact> selectContactList(PageInfo contactPi, String memberNo);

    int deleteContact(String questionNo);

    MemberContact contactDetail(String questionNo);

    ArrayList<MemberContact> selectAnswer(String memberNo);

    ArrayList<MemberContact> selectAnswerList(PageInfo answerPi, String memberNo);

    // 리뷰
    ArrayList<MemberReview> selectReview(String memberNo);

    ArrayList<MemberReview> selectReviewList(PageInfo pi, String memberNo);

    MemberReview findReview(String reviewNo);

    int updateReview(MemberReview mr);

    int deleteReview(String reviewNo);

    String[] selectReviewImage(String reviewNo);

    int insertReviewImage(HashMap<String, String> hs);

    int deleteReviewImage(HashMap<String, String> hs);

    // 주문
    ArrayList<MemberOrder> selectOrder(String memberNo);

    ArrayList<MemberOrder> selectOrderList(PageInfo pi, String memberNo);

    ArrayList<MemberOrder> orderDetail(HashMap<String, String> hm);

    
    int checkId(String memberId);

    int checkNickname(String memberNickname);

    ArrayList<Member> findId(Member m);

    Member findPassword(Member m);

    int changePassword(Member m);

    Member kakaoFindId(String memberId);

    Admin adminLogin(Admin a);

    ArrayList<MemberCoupon> findEnrollCoupon();

    void enrollCoupon(MemberCoupon coupon);

    
}
