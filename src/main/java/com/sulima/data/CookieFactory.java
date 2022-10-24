package com.sulima.data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieFactory {

    public static void addCookie(HttpServletResponse response, String login) {
        Cookie cookie = new Cookie("login", login);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie el : cookies) {
                if (el.getName().contains(name)) {
                    return el;
                }
            }
        }
        return null;
    }

    public static void deleteCookieByName(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie el : cookies) {
            if (el.getName().contains(name)) {
                el.setMaxAge(0);
                response.addCookie(el);
                break;
            }
        }
    }
}
