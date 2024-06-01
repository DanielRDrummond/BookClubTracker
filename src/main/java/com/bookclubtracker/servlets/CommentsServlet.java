package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve discussion ID from request parameter
        int discussionId = Integer.parseInt(request.getParameter("discussionId"));
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to fetch comments data for the specified discussion
        String query = "SELECT * FROM comments WHERE discussion_id = ?";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameter
            preparedStatement.setInt(1, discussionId);
            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Convert result set to JSON using Gson library
            Gson gson = new Gson();
            JsonArray commentsArray = new JsonArray();
            while (resultSet.next()) {
                JsonObject commentObj = new JsonObject();
                commentObj.addProperty("content", resultSet.getString("content"));
                commentObj.addProperty("creationDate", resultSet.getString("creation_date"));
                // Add more properties as needed
                commentsArray.add(commentObj);
            }
            String commentsJson = gson.toJson(commentsArray);
            
            // Set response content type
            response.setContentType("application/json");
            // Get PrintWriter object
            PrintWriter out = response.getWriter();
            // Send JSON response
            out.println(commentsJson);
            
            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error fetching comments from database.");
        }
    }
}
