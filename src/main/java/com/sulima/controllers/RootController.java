package com.sulima.controllers;

import com.sulima.data.ArticlesService;
import com.sulima.data.MainService;
import com.sulima.model.Articles;
import com.sulima.model.Comments;
import com.sulima.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RootController {

    private final ArticlesService articlesService;
    private final MainService mainService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("currentTitle", "PHP блог на Java с Thymeleaf");
        model.addAttribute("articles", articlesService.getListArticles());
        model.addAttribute("loginByCookies", mainService.getUserByCookie(request));
        return "index";
    }

    @GetMapping("/news.html/{id}")
    public String news(@PathVariable Integer id, Model model, HttpServletRequest request) {
        initialAttributes(id, model, request);
        return "news";
    }

    @PostMapping("/news")
    public String addComment(@Valid Comments comment, BindingResult bindingResult, Model model) {
        String code = mainService.getError(bindingResult);
        if (code == "1") {
            articlesService.saveComment(comment);
        } else {
            // TODO: 20.10.2022 Здесь не получилось вывести ошибку. Когда в строке есть пагинация, то не получается
//            model.addAttribute("errorBlock", code);
//            return "news";
        }
        return "redirect:/news.html/" + comment.getArticleId();
    }

    private void initialAttributes(Integer id, Model model, HttpServletRequest request) {
        Articles article = articlesService.getArticleById(id);
        Users userByCookie = mainService.getUserByCookie(request);
        Comments newComment = articlesService.fillDataFromNewComment(id, userByCookie);
        model.addAttribute("currentTitle", "Статья блога");
        model.addAttribute("comments", articlesService.getListComments(id));
        model.addAttribute("commentNew", newComment);
        model.addAttribute("articleOne", article);
        model.addAttribute("timePublic", articlesService.getDateInHuman(article.getDate()));
        model.addAttribute("loginByCookies", userByCookie);
        model.addAttribute("errorComment", mainService.getErrorAllowedComment(article, userByCookie));
        model.addAttribute("errorBlock", "1");
    }
}

//    @Query(value = "SELECT " +
//            "*, " +
//            "(SELECT COUNT(*) FROM comments c WHERE a.id = c.article_id) AS count " +
//            "FROM articles a"
//            , nativeQuery = true)
//    List<ArticlesComments> findAllArticlesAndCountComments();