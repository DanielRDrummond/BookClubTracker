package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class CreateBookClubServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve book club data from request parameters
        String clubName = request.getParameter("clubName");
        String description = request.getParameter("description");
        String creator = request.getParameter("creator"); // Assuming this comes from session
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/bookclub";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to insert new book club into database
        String query = "INSERT INTO book_clubs (club_name, description, creator) VALUES (?, ?, ?)";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters
            preparedStatement.setString(1, clubName);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, creator);
            // Execute update (insert) query
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Check if book club creation was successful
            if (rowsAffected > 0) {
                // Book club creation successful, redirect to home page or book club details page
                response.sendRedirect("index.html");
            } else {
                // Book club creation failed, redirect to create club page with error message
                response.sendRedirect("create-club.html?error=creationFailed");
            }
            
            // Close connections
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("create-club.html?error=dbError");
        }
    }
}
