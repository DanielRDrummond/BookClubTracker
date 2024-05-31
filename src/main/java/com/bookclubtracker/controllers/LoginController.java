package com.bookclubtracker.controllers;

import com.bookclubtracker.model.User;
import com.bookclubtracker.utils.DatabaseUtil;
import com.bookclubtracker.utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = DatabaseUtil.getUserByUsername(username);

        if (user != null && PasswordUtil.checkPassword(password, user.getHashedPassword())) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("validate-2fa.html");
        } else {
            response.sendRedirect("login.html?error=invalid");
        }
    }
}
