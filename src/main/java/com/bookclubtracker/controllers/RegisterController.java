package com.bookclubtracker.controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bookclubtracker";
    private static final String JDBC_USER = "yourusername";
    private static final String JDBC_PASSWORD = "yourpassword";

    // SQL query to insert a new user into the database
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if (username == null || email == null || password == null || confirmPassword == null || 
            username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("register.html").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("register.html").forward(request, response);
            return;
        }

        try {
            String hashedPassword = hashPassword(password);

            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, hashedPassword);

                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    response.sendRedirect("login.html");
                } else {
                    request.setAttribute("errorMessage", "Registration failed. Please try again.");
                    request.getRequestDispatcher("register.html").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Database error. Please try again.");
                request.getRequestDispatcher("register.html").forward(request, response);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing password. Please try again.");
            request.getRequestDispatcher("register.html").forward(request, response);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
