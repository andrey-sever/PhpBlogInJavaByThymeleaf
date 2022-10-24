package com.sulima.data;

import com.sulima.model.Articles;
import com.sulima.model.LoginPassword;
import com.sulima.model.Users;
import com.sulima.dao.UsersRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class MainService {

    private final UsersRepository usersRepository;
    private final String HASH_PLUS = "10zM92La3874QIUw56";

    public MainService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void saveUser(Users user) {
        user.setPass(getPasswordMd5(user.getPass()));
        usersRepository.save(user);
    }

    public String getError(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return "1";
        }
        ObjectError firstError = bindingResult.getAllErrors().get(0);
        return "Ошибка. ".concat(firstError.getDefaultMessage());
    }

    public String getErrorLoginPass(BindingResult bindingResult, LoginPassword loginPassword) {

        String result = getError(bindingResult);
        if (result == "1") {
            if (!usersRepository.existsByLoginAndPass(loginPassword.getLogin(), getPasswordMd5(loginPassword.getPass()))) {
                result = "Пользователь не найден.";
            }
        }
        return result;
    }

    public String getErrorReg(BindingResult bindingResult, Users user) {
        String result = getError(bindingResult);
        if (result == "1") {
            if (usersRepository.existsByLogin(user.getLogin())) {
                result = "Пользователь существует.";
            }
        }
        return result;
    }

    public String getErrorAllowedComment(Articles article, Users userByCookie) {
        String error = "";
        if (userByCookie == null) {
            error = "Чтобы оставлять комментарий нужно зарегистрироваться.";
        } else if (userByCookie.getLogin().contains(article.getAuthor())) {
            error = "Нельзя оставлять комментарий под своей статьей.";
        }
        return error;
    }

    public Users getByLogin(String login) {
        return usersRepository.findByLogin(login);
    }

    private String getPasswordMd5(String pass) {
        return DigestUtils.md5Hex(pass.concat(HASH_PLUS)).toUpperCase();
    }

    public Users getUserByCookie(HttpServletRequest request) {
        Users userByCookie = null;
        Cookie cookie = CookieFactory.getCookieByName(request, "login");
        if (cookie != null) {
            userByCookie = getByLogin(cookie.getValue());
        }
        return userByCookie;
    }
}
