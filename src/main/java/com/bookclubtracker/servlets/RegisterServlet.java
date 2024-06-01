package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve registration data from request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to insert new user into database
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            // Execute update (insert) query
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Check if user registration was successful
            if (rowsAffected > 0) {
                // Registration successful, redirect to login page
                response.sendRedirect("login.html?registration=success");
            } else {
                // Registration failed, redirect to registration page with error message
                response.sendRedirect("register.html?error=registrationFailed");
            }
            
            // Close connections
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("register.html?error=dbError");
        }
    }
}
