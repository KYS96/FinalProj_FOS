package com.kh.slumber.member.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.member.model.vo.Member;
import com.kh.slumber.member.model.vo.MemberBoard;
import com.kh.slumber.member.model.vo.MemberContact;
import com.kh.slumber.member.model.vo.MemberCoupon;
import com.kh.slumber.member.model.vo.MemberOrder;
import com.kh.slumber.member.model.vo.MemberReply;
import com.kh.slumber.member.model.vo.MemberReview;


@Mapper
public interface MemberDAO {

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

    //페이징
    int getBoardListCount(String memberNo);

    int getReplyListCount(String memberNo);

    int getContactListCount(String memberNo);

    int getAnswerListCount(String memberNo);

    int getOrderListCount(String memberNo);

    // 쿠폰
    ArrayList<MemberCoupon> selectCurrentCoupon(String memberNo);

    ArrayList<MemberCoupon> selectUseCoupon(String memberId);

    ArrayList<MemberCoupon> selectEndCoupon(String memberNo);

    MemberCoupon couponDetail(String couponIssuanceNo);

    // 게시판, 댓글
    ArrayList<MemberBoard> selectBoard(String memberNo);

    ArrayList<MemberBoard> selectBoardList(String memberNo, RowBounds rowBounds);

    int deleteBoard(String boardNo);

    ArrayList<MemberReply> selectReply(String memberNo);

    ArrayList<MemberReply> selectReplyList(String memberNo, RowBounds rowBounds);

    int deleteReply(String replyNo);

    int countReply(String boardNo);

    // 문의
    ArrayList<MemberContact> selectContact(String memberNo);

    ArrayList<MemberContact> selectContactList(String memberNo, RowBounds pagination);

    int deleteContact(String questionNo);

    MemberContact contactDetail(String questionNo);

    ArrayList<MemberContact> selectAnswer(String memberNo);

    ArrayList<MemberContact> selectAnswerList(String memberNo, RowBounds pagination);

    // 리뷰
    ArrayList<MemberReview> selectReview(String memberNo);

    ArrayList<MemberReview> selectReviewList(String memberNo, RowBounds rowBounds);

    MemberReview findReview(String reviewNo);

    int updateReview(MemberReview mr); 

    int deleteReview(String reviewNo);

    String[] selectReviewImage(String reviewNo);

    int insertReviewImage(HashMap<String, String> hs);

    int deleteReviewImage(HashMap<String, String> hs);

    // 주문
    ArrayList<MemberOrder> selectOrder(String memberNo);

    ArrayList<MemberOrder> selectOrderList(String memberNo, RowBounds rowBounds);

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
