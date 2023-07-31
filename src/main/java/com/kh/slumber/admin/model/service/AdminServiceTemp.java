package com.kh.slumber.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.slumber.admin.model.dao.AdminDAOTemp;
import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.vo.Member;

@Service
public class AdminServiceTemp {
    
    @Autowired
    private AdminDAOTemp aTDAO;

    public int getListCount(HashMap<String, String> hm, String type) {
        if(type.equals("member")) {
            return aTDAO.getMemberListCount(hm);
        }

        if(type.equals("admin")) {
            return aTDAO.getAdminListCount(hm);
        }

        return 0;
    }

    public ArrayList<Member> selectMember(HashMap<String, String> hm, PageInfo pi) {
        return aTDAO.selectMember(hm, pagination(pi));
    }

    public ArrayList<Admin> selectAdmin(HashMap<String, String> hm, PageInfo pi) {
        return aTDAO.selectAdmin(hm, pagination(pi));
    }

    public RowBounds pagination(PageInfo pi) {
		return new RowBounds((pi.getCurrentPage() - 1) * pi.getBoardLimit(), pi.getBoardLimit());
	}

    public int updateBlacklist(HashMap<String, String> hm) {
        return aTDAO.updateBlacklist(hm);
    }

    public int updateStatus(HashMap<String, String> hm) {
        return aTDAO.updateStatus(hm);
    }

    public int updateAdmin(HashMap<String, String> hm) {
        return aTDAO.updateAdmin(hm);
    }

    public int insertAdmin(Admin admin) {
        return aTDAO.insertAdmin(admin);
    }

    public int checkAdminId(String adminId) {
        return aTDAO.checkAdminId(adminId);
    }

    public int checkAdminPhone(String adminPhone) {
        return aTDAO.checkAdminPhone(adminPhone);
    }

    public int updateAdminPwd(Admin admin) {
        return aTDAO.updateAdminPwd(admin);
    }

    public Admin findAdminId(String adminPhone) {
        return aTDAO.findAdminId(adminPhone);
    }

    public Member memberDetail(String memberNo) {
        return aTDAO.memberDetail(memberNo);
    }

    public Admin adminDetail(String adminNo) {
        return aTDAO.adminDetail(adminNo);
    }
}
