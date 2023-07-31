package com.kh.slumber.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.slumber.member.model.service.KakaoService;
import com.kh.slumber.member.model.vo.KakaoDTO;
import com.kh.slumber.member.model.vo.MsgEntity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception {
        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        return ResponseEntity.ok()
                .body(new MsgEntity("Success", kakaoInfo));
    }


}