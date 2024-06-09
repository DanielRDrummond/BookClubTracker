package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve username and password from request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/bookclub";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to validate user credentials
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Check if user exists
            if (resultSet.next()) {
                // User authenticated, set session attribute
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                // Redirect to home page or user dashboard
                response.sendRedirect("index.html"); // Example redirect
            } else {
                // Invalid credentials, redirect to login page with error message
                response.sendRedirect("login.html?error=invalidCredentials");
            }
            
            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("login.html?error=dbError");
        }
    }
}
