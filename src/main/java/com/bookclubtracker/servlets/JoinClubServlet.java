package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class JoinClubServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve user and club information from request parameters
        String username = request.getParameter("username"); // Assuming this comes from session
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        String role = request.getParameter("role"); // Assuming this comes from session
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/bookclub";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to insert new membership into database
        String query = "INSERT INTO club_members (username, club_id, role) VALUES (?, ?, ?)";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameters
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, clubId);
            preparedStatement.setString(3, role);
            // Execute update (insert) query
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Check if membership creation was successful
            if (rowsAffected > 0) {
                // Membership creation successful, redirect to home page or club details page
                response.sendRedirect("index.html");
            } else {
                // Membership creation failed, redirect to join club page with error message
                response.sendRedirect("join-club.html?error=joinFailed");
            }
            
            // Close connections
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("join-club.html?error=dbError");
        }
    }
}
