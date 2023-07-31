package com.kh.slumber.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.dao.MemberDAO;
import com.kh.slumber.member.model.vo.Member;
import com.kh.slumber.member.model.vo.MemberBoard;
import com.kh.slumber.member.model.vo.MemberContact;
import com.kh.slumber.member.model.vo.MemberCoupon;
import com.kh.slumber.member.model.vo.MemberOrder;
import com.kh.slumber.member.model.vo.MemberReply;
import com.kh.slumber.member.model.vo.MemberReview;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO mDAO;

	@Override
	public Member login(Member m) {
		return mDAO.login(m);
	}

	@Override
	public int enrollMember(Member m) {
		return mDAO.enrollMember(m);
	}

	// 마이페이지
	@Override
	public int checkDuplication(String memberNickname) {
		return mDAO.checkDuplication(memberNickname);
	}

	@Override
	public int checkMyNickname(Member m) {
		return mDAO.checkMyNickname(m);
	}

	@Override
	public int updateMember(Member m) {
		return mDAO.updateMember(m);
	}

	// 알람
	@Override
	public ArrayList<MemberCoupon> alarmCoupon(String memberNo) {
		return mDAO.alarmCoupon(memberNo);
	}

	@Override
	public int deleteCouponAlarm(String memberNo) {
		return mDAO.deleteCouponAlarm(memberNo);
	}

	@Override
	public ArrayList<MemberReply> alarmReply(String boardNo) {
		return mDAO.alarmReply(boardNo);
	}

	@Override
	public int deleteReplyAlarm(String replyNo) {
		return mDAO.deleteReplyAlarm(replyNo);
	}

	@Override
	public int checkAlarm(String replyNo) {
		return mDAO.checkAlarm(replyNo);
	}

	// 페이징
	@Override
	public RowBounds pagination(PageInfo pi) {
		return new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), pi.getBoardLimit());
	}

	@Override
	public int getListCount(String type, String memberNo) {
		switch(type) {
			case "order": return mDAO.getOrderListCount(memberNo);
			case "board": return mDAO.getBoardListCount(memberNo);
			case "reply": return mDAO.getReplyListCount(memberNo);
			case "contact": return mDAO.getContactListCount(memberNo);
			case "answer": return mDAO.getAnswerListCount(memberNo);
			default : return 0;
		}
	}

	// 쿠폰
	@Override
	public ArrayList<MemberCoupon> selectCurrentCoupon(String memberNo) {
		return mDAO.selectCurrentCoupon(memberNo);
	}

	@Override
	public ArrayList<MemberCoupon> selectUseCoupon(String memberId) {
		return mDAO.selectUseCoupon(memberId);
	}
	
	@Override
	public ArrayList<MemberCoupon> selectEndCoupon(String memberNo) {
		return mDAO.selectEndCoupon(memberNo);
	}

	@Override
	public MemberCoupon couponDetail(String couponIssuanceNo) {
		return mDAO.couponDetail(couponIssuanceNo);
	}

	// 게시판, 댓글
	@Override
	public ArrayList<MemberBoard> selectBoard(String memberNo) {
		return mDAO.selectBoard(memberNo);
	}

	@Override
	public ArrayList<MemberBoard> selectBoardList(PageInfo boardPi, String memberNo) {
		return mDAO.selectBoardList(memberNo, pagination(boardPi));
	}

	@Override
	public int deleteBoard(String boardNo) {
		return mDAO.deleteBoard(boardNo);
	}

	@Override
	public ArrayList<MemberReply> selectReply(String memberNo) {
		return mDAO.selectReply(memberNo);
	}

	@Override
	public ArrayList<MemberReply> selectReplyList(PageInfo replyPi, String memberNo) {
		return mDAO.selectReplyList(memberNo, pagination(replyPi));	
	}

	@Override
	public int deleteReply(String replyNo) {
		return mDAO.deleteReply(replyNo);
	}

	@Override
	public int countReply(String boardNo) {
		return mDAO.countReply(boardNo);
	}

	// 문의
	@Override
	public ArrayList<MemberContact> selectContact(String memberNo) {
		return mDAO.selectContact(memberNo);
	}
	
	@Override
	public ArrayList<MemberContact> selectContactList(PageInfo productContactPi, String memberNo) {
		return mDAO.selectContactList(memberNo, pagination(productContactPi));
	}

	@Override
	public int deleteContact(String questionNo) {
		return mDAO.deleteContact(questionNo);
	}

	@Override
	public MemberContact contactDetail(String questionNo) {
		return mDAO.contactDetail(questionNo);
	}

	@Override
	public ArrayList<MemberContact> selectAnswer(String memberNo) {
		return mDAO.selectAnswer(memberNo);
	}

	@Override
	public ArrayList<MemberContact> selectAnswerList(PageInfo answerPi, String memberNo) {
		return mDAO.selectAnswerList(memberNo, pagination(answerPi));
	}

	// 리뷰
	@Override
	public ArrayList<MemberReview> selectReview(String memberNo) {
		return mDAO.selectReview(memberNo);
	}

	@Override
	public ArrayList<MemberReview> selectReviewList(PageInfo pi, String memberNo) {
		return mDAO.selectReviewList(memberNo, pagination(pi));
	}

	@Override
	public MemberReview findReview(String reviewNo) {
		return mDAO.findReview(reviewNo);
	}

	@Override
	public int updateReview(MemberReview mr) {
		return mDAO.updateReview(mr);
	}

	@Override
	public int deleteReview(String reviewNo) {
		return mDAO.deleteReview(reviewNo);
	}

	@Override
	public String[] selectReviewImage(String reviewNo) {
		return mDAO.selectReviewImage(reviewNo);
	}

	@Override
	public int insertReviewImage(HashMap<String, String> hs) {
		return mDAO.insertReviewImage(hs);
	}

	@Override
	public int deleteReviewImage(HashMap<String, String> hs) {
		return mDAO.deleteReviewImage(hs);
	}

	// 주문
	@Override
	public ArrayList<MemberOrder> selectOrder(String memberNo) {
		return mDAO.selectOrder(memberNo);
	}

	@Override
	public ArrayList<MemberOrder> selectOrderList(PageInfo pi, String memberNo) {
		return mDAO.selectOrderList(memberNo, pagination(pi));
	}

	@Override
	public ArrayList<MemberOrder> orderDetail(HashMap<String, String> hm) {
		return mDAO.orderDetail(hm);
	}

	
	@Override
	public int checkId(String memberId) {
		return mDAO.checkId(memberId);
	}

	@Override
	public int checkNickname(String memberNickname) {
		return mDAO.checkNickname(memberNickname);
	}

	public ArrayList<Member> findId(Member m) {
		return mDAO.findId(m);
	}

	@Override
	public Member findPassword(Member m) {
		return mDAO.findPassword(m);
	}

	@Override
	public int changePassword(Member m) {
		return mDAO.changePassword(m);
	}

	@Override
	public Member kakaoFindId(String memberId) {
		return mDAO.kakaoFindId(memberId);
	}

	@Override
	public Admin adminLogin(Admin a) {
		return mDAO.adminLogin(a);
	}

	@Override
	public ArrayList<MemberCoupon> findEnrollCoupon() {
		return mDAO.findEnrollCoupon();
	}

	@Override
	public void enrollCoupon(MemberCoupon coupon) {
		mDAO.enrollCoupon(coupon);
	}


}