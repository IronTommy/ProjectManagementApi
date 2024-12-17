package com.example.projects.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/setCookie")
public class SetCookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("jwt", "jwt_value");
        jwtCookie.setMaxAge(30 * 60);
        jwtCookie.setPath("/");
        String sameSiteInfo = "SameSite=Lax";
        response.addHeader("Set-Cookie", jwtCookie.getName() + "=" + jwtCookie.getValue() + "; Max-Age=" + jwtCookie.getMaxAge() + "; Path=" + jwtCookie.getPath() + "; " + sameSiteInfo + "; Secure; HttpOnly");
        response.getWriter().println("Cookie установлен");
    }
}
