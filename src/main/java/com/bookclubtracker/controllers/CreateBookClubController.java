package com.bookclubtracker.controllers;

import com.bookclubtracker.model.BookClub;
import com.bookclubtracker.model.User;
import com.bookclubtracker.utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateBookClubController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the current user from the session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Get parameters from the request
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Validate inputs
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            response.sendRedirect("create-club.html?error=invalid_input");
            return;
        }

        // Create a new BookClub object
        BookClub bookClub = new BookClub();
        bookClub.setName(name);
        bookClub.setDescription(description);
        bookClub.setCreator(user.getUsername());
        bookClub.setCreationDate(new java.util.Date());

        // Save the BookClub to the database
        boolean success = DatabaseUtil.createBookClub(bookClub);

        // Redirect to the appropriate page based on success or failure
        if (success) {
            response.sendRedirect("view-clubs.html");
        } else {
            response.sendRedirect("create-club.html?error=database_error");
        }
    }
}