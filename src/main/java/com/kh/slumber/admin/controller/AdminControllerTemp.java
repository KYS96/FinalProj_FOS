package com.kh.slumber.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.kh.slumber.admin.model.service.AdminServiceTemp;
import com.kh.slumber.admin.model.vo.Admin;
import com.kh.slumber.common.Pagination;
import com.kh.slumber.common.model.vo.PageInfo;
import com.kh.slumber.member.model.vo.Member;

@Controller
public class AdminControllerTemp {
    
    @Autowired
    private AdminServiceTemp aTService;
    
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @RequestMapping("memberManageAdmin.admin")
    public String memberManageAdmin(){
        return "memberManageAdmin";
    }

    @RequestMapping("enrollAdmin.admin")
    public String enrollAdmin() {
        return "enrollAdmin";
    }

    @RequestMapping("insertAdmin.admin")
    public String insertAdmin(Admin admin, String adminPwdCheck, Model model) {
        int result = 0;

        if(admin.getAdminPwd().equals(adminPwdCheck)) {
            admin.setAdminPwd(bcrypt.encode(adminPwdCheck));
        
            result = aTService.insertAdmin(admin);
        }

        if(result == 1) {
            model.addAttribute("msg", "success");
        } else {
            model.addAttribute("msg", "fail");
        }

        return "enrollAdmin";
    }

    @RequestMapping("changePwd.admin")
    public String changePwd() {
        return "changePwd";
    }

    @RequestMapping("updateAdminPwd.admin")
    public String updateAdminPwd(Admin admin, String adminPwdCheck, Model model) {
        int result = 0;
        
        if(admin.getAdminPwd().equals(adminPwdCheck)) {
            admin.setAdminPwd(bcrypt.encode(adminPwdCheck));
        
            result = aTService.updateAdminPwd(admin);
        }

        if(result == 1) {
            model.addAttribute("msg", "success");
        } else {
            model.addAttribute("msg", "fail");
        }

        return "changePwd";
    }

    @RequestMapping("findId.admin")
    public String findId() {
        return "findId";
    }

    @RequestMapping("findAdminId.admin")
    public String findAdminId(Admin admin, Model model) {
        Admin result = aTService.findAdminId(admin.getAdminPhone());

        if(result != null) {
            model.addAttribute("msg", "success");
            model.addAttribute("adminId", result.getAdminId());
        } else {
            model.addAttribute("msg", "fail");
        }

        return "findId";
    }

    @RequestMapping("checkAdminId.admin")
    @ResponseBody
    public Integer checkAdminId(String adminId) {
        int result = aTService.checkAdminId(adminId);

        return result;
    }

    @RequestMapping("checkAdminPhone.admin")
    @ResponseBody
    public Integer checkAdminPhone(String adminPhone) {
        int result = aTService.checkAdminPhone(adminPhone);

        return result;
    }

    @RequestMapping("searchMemberList.admin")
    @ResponseBody
    public ResponseEntity<String> searchMemberList(@RequestParam(value="page", required=false) Integer page, String type, String keyword) {
        int currentPage = 1;
        
        if(page != null) {
            currentPage = page;
        }

        HashMap<String, String> hm = new HashMap<>();

        hm.put("type", type);
        hm.put("keyword", keyword);

        int listCount = aTService.getListCount(hm, "member");
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<Member> list = aTService.selectMember(hm, pi);

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("type", type);
        context.setVariable("keyword", keyword);
        context.setVariable("currentPage", pi.getCurrentPage());

        return ResponseEntity.ok(templateEngine.process("searchMember", context));  
    }

    @RequestMapping("searchAdminList.admin")
    @ResponseBody
    public ResponseEntity<String> searchAdminList(@RequestParam(value="page", required=false) Integer page, String type, String keyword) {
        int currentPage = 1;
        
        if(page != null) {
            currentPage = page;
        }

        HashMap<String, String> hm = new HashMap<>();

        hm.put("type", type);
        hm.put("keyword", keyword);

        int listCount = aTService.getListCount(hm, "admin");
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<Admin> list = aTService.selectAdmin(hm, pi);

        Context context = new Context();

        context.setVariable("list", list);
        context.setVariable("type", type);
        context.setVariable("keyword", keyword);
        context.setVariable("currentPage", pi.getCurrentPage());

        return ResponseEntity.ok(templateEngine.process("searchAdmin", context));  
    }

    @RequestMapping("updateBlacklist.admin")
    @ResponseBody
    public String updateBlacklist(String memberNo, String memberIsBlacklist, String type, String keyword, String currentPage) {
        HashMap<String, String> hm = new HashMap<>();

        hm.put("memberNo", memberNo);

        if(memberIsBlacklist.equals("Y")) {
            hm.put("memberIsBlacklist", "N");
        } else {
            hm.put("memberIsBlacklist", "Y");
        }

        int result = aTService.updateBlacklist(hm);

        if(result >= 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("updateStatus.admin")
    @ResponseBody
    public String updateStatus(String memberNo, String memberStatus, String type, String keyword, String currentPage) {
        HashMap<String, String> hm = new HashMap<>();

        hm.put("memberNo", memberNo);

        if(memberStatus.equals("Y")) {
            hm.put("memberStatus", "N");
        } else {
            hm.put("memberStatus", "Y");
        }

        int result = aTService.updateStatus(hm);

        if(result >= 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("updateAdmin.admin")
    @ResponseBody
    public String updateAdmin(String adminNo, String adminStatus, String type, String keyword, String currentPage) {
        HashMap<String, String> hm = new HashMap<>();

        hm.put("adminNo", adminNo);

        if(adminStatus.equals("Y")) {
            hm.put("adminStatus", "N");
        } else {
            hm.put("adminStatus", "Y");
        }

        int result = aTService.updateAdmin(hm);

        if(result >= 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("pagination.admin")
    @ResponseBody
    public ResponseEntity<String> pagination(@RequestParam(value="page", required=false) Integer page, String type, String keyword, String func, String method) {
        int currentPage = 1;
        
        if(page != null) {
            currentPage = page;
        }

        HashMap<String, String> hm = new HashMap<>();

        hm.put("type", type);
        hm.put("keyword", keyword);

        int listCount = aTService.getListCount(hm, method);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
        
        Context context = new Context();
        context.setVariable("pi", pi);
        context.setVariable("type", type);
        context.setVariable("keyword", keyword);
        context.setVariable("func", func);

        return ResponseEntity.ok(templateEngine.process("memberPaging", context));
    }

    @RequestMapping("memberDetail.admin")
    @ResponseBody
    public ResponseEntity<String> memberDetail(String memberNo) {
        Member m = aTService.memberDetail(memberNo);

        Context context = new Context();
        context.setVariable("m", m);

        return ResponseEntity.ok(templateEngine.process("memberDetail", context));
    }

    @RequestMapping("adminDetail.admin")
    @ResponseBody
    public ResponseEntity<String> adminDetail(String adminNo) {
        Admin a = aTService.adminDetail(adminNo);

        Context context = new Context();
        context.setVariable("a", a);

        return ResponseEntity.ok(templateEngine.process("adminDetail", context));
    }
}
