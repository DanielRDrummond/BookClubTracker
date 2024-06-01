package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve username from session attribute
        HttpSession session = request.getSession(false);
        String username = null;
        if (session != null) {
            username = (String) session.getAttribute("username");
        }
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to fetch user profile data
        String query = "SELECT * FROM users WHERE username = ?";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameter
            preparedStatement.setString(1, username);
            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Set response content type
            response.setContentType("text/html");
            // Get PrintWriter object
            PrintWriter out = response.getWriter();

            // Send user profile data to HTML page
            out.println("<html><body>");
            if (resultSet.next()) {
                out.println("<h2>User Profile</h2>");
                out.println("<p><strong>Username:</strong> " + resultSet.getString("username") + "</p>");
                out.println("<p><strong>Email:</strong> " + resultSet.getString("email") + "</p>");
                // Add more profile details as needed
            } else {
                out.println("<p>User profile not found.</p>");
            }
            out.println("</body></html>");

            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("error.html");
        }
    }
}
