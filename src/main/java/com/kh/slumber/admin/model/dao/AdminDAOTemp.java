package com.kh.slumber.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.member.model.vo.Member;

@Mapper
public interface AdminDAOTemp {

    int getMemberListCount(HashMap<String, String> hm);

    int getAdminListCount(HashMap<String, String> hm);

    ArrayList<Member> selectMember(HashMap<String, String> hm, RowBounds rowBounds);

    ArrayList<Admin> selectAdmin(HashMap<String, String> hm, RowBounds pagination);

    int updateBlacklist(HashMap<String, String> hm);

    int updateStatus(HashMap<String, String> hm);

    int updateAdmin(HashMap<String, String> hm);

    int insertAdmin(Admin admin);

    int checkAdminId(String adminId);

    int checkAdminPhone(String adminPhone);

    int updateAdminPwd(Admin admin);

    Admin findAdminId(String adminPhone);

    Member memberDetail(String memberNo);

    Admin adminDetail(String adminNo);
}
