package com.sulima.controllers;

import com.sulima.data.ArticlesService;
import com.sulima.data.CookieFactory;
import com.sulima.data.MainService;
import com.sulima.model.Articles;
import com.sulima.model.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

//    @PostMapping("/list_articles")
//    public List<Articles> listArticles() {
//        return articlesService.getListArticles();
//    }

//    @GetMapping(path = "/article_one/{id}")
//    public Articles articleOne(@PathVariable Integer id) {
//        return articlesService.getArticleById(id);
//    }

//    @PostMapping("/comments_list")
//    public List<Comments> commentsList(@RequestParam Integer id) {
//        return articlesService.getListComments(id);
//    }

//    @PostMapping("/comment_add")
//    public String commentAdd(@Validated Comments comment, BindingResult bindingResult) {
//        String error = mainService.getError(bindingResult);
//        if (error == "1") {
//            articlesService.saveComment(comment);
//        }
//        return error;
//    }
}

