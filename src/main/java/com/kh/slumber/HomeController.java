package com.kh.slumber;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kh.slumber.market.model.service.MarketService;
import com.kh.slumber.market.model.vo.MarketProduct;

import com.kh.slumber.member.model.service.KakaoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final KakaoService kakaoService;
  
  @Autowired
    private MarketService marketService;

    @GetMapping("/")
    public String index(Model model) {
        
        ArrayList<MarketProduct> beds = marketService.getIndexProduct("침대");
        ArrayList<MarketProduct> blankets = marketService.getIndexProduct("이불");
        ArrayList<MarketProduct> pillows = marketService.getIndexProduct("베개");
        ArrayList<MarketProduct> goods = marketService.getIndexProduct("굿즈");
        ArrayList<MarketProduct> clothings = marketService.getIndexProduct("의류");
        ArrayList<MarketProduct> healths = marketService.getIndexProduct("건강");
        model.addAttribute("beds", beds);
        model.addAttribute("blankets", blankets);
        model.addAttribute("pillows", pillows);
        model.addAttribute("goods", goods);
        model.addAttribute("clothings", clothings);
        model.addAttribute("healths", healths);

        return "index";
    }

    @GetMapping("home.do")
    public String home() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "index";
    }

    @GetMapping("errorPage")
    public String errorPage(String type, Model model) {
        model.addAttribute("type", type);

        return "errorPage";
    }
}
