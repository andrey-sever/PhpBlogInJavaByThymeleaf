package com.sulima.controllers;

import com.sulima.data.ArticlesService;
import com.sulima.data.CookieFactory;
import com.sulima.data.MainService;
import com.sulima.model.Articles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ArticlesController {

    private final MainService mainService;
    private final ArticlesService articlesService;

    @GetMapping("/article")
    public String article(HttpServletRequest request, Model model) {
        Cookie cookie = CookieFactory.getCookieByName(request, "login");
        if (cookie == null) {
            return "redirect:/reg";
        }
        model.addAttribute("currentTitle", "Добавление статьи");
        model.addAttribute("loginByCookies", cookie.getValue());
        model.addAttribute("article", new Articles());
        model.addAttribute("errorBlock", "1");
        model.addAttribute("successBlock", "");
        return "article";
    }

    @PostMapping("/article")
    public String articleAdd(@Valid Articles article, BindingResult bindingResult, HttpServletRequest request,
                             Model model) {
        String code = mainService.getError(bindingResult);
        if (code == "1") {
            articlesService.saveArticle(article, request);
            model.addAttribute("successBlock", "1");
            model.addAttribute("errorBlock", "1");
            model.addAttribute("article", article);
        } else {
            model.addAttribute("successBlock", "");
            model.addAttribute("errorBlock", code);
            model.addAttribute("article", article);
        }
        return "/article";
    }
}

