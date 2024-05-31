package com.bookclubtracker.servlets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/secure/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("username") != null;

        if (loggedIn) {
            // User is logged in, continue with the request
            filterChain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}
