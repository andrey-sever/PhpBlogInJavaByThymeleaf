package com.sulima.controllers;

import com.sulima.data.CookieFactory;
import com.sulima.data.MainService;
import com.sulima.model.LoginPassword;
import com.sulima.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MainService mainService;

    @GetMapping("/auth")
    public String auth(Model model, HttpServletRequest request) {
        Users userByCookie = mainService.getUserByCookie(request);
        model.addAttribute("currentTitle", "Авторизация");
        model.addAttribute("loginByCookies", userByCookie);
        model.addAttribute("loginPassword", new LoginPassword());
        model.addAttribute("errorBlock", "1");
        return "auth";
    }

    @PostMapping("/auth")
    public String authorization(@Valid LoginPassword loginPassword, BindingResult bindingResult,
                                HttpServletResponse response, Model model) {
        String code = mainService.getErrorLoginPass(bindingResult, loginPassword);

        //установим куки
        if (code == "1") {
            CookieFactory.addCookie(response, loginPassword.getLogin());
        } else {
            model.addAttribute("errorBlock", code);
            return "/auth";
        }
        return "redirect:/auth";
    }

    @PostMapping("/authExit")
    public String exitAuthorization(HttpServletRequest request, HttpServletResponse response) {
        CookieFactory.deleteCookieByName(request, response, "login");
        return "redirect:/";
    }
}
