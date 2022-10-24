package com.sulima.controllers;

import com.sulima.data.MainService;
import com.sulima.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegController {

    private final MainService mainService;

    @GetMapping("/reg")
    public String reg(Model model) {
        model.addAttribute("currentTitle", "Регистрация");
        model.addAttribute("user", new Users());
        model.addAttribute("errorBlock", "1");
        return "reg";
    }

    @PostMapping("/reg")
    public String registration(@Valid Users user, BindingResult bindingResult, Model model) {
        String code = mainService.getErrorReg(bindingResult, user);
        if (code == "1") {
            mainService.saveUser(user);
        } else {
            model.addAttribute("errorBlock", code);
            model.addAttribute("user", user);
            return "/reg";
        }
        return "redirect:/";
    }
}
