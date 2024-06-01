package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MeetingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Retrieve club ID from request parameter
        int clubId = Integer.parseInt(request.getParameter("clubId"));
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to fetch meetings data for the specified club
        String query = "SELECT * FROM meetings WHERE club_id = ?";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameter
            preparedStatement.setInt(1, clubId);
            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Convert result set to JSON using Gson library
            Gson gson = new Gson();
            JsonArray meetingsArray = new JsonArray();
            while (resultSet.next()) {
                JsonObject meetingObj = new JsonObject();
                meetingObj.addProperty("date", resultSet.getString("meeting_date"));
                meetingObj.addProperty("location", resultSet.getString("location"));
                meetingObj.addProperty("description", resultSet.getString("description"));
                // Add more properties as needed
                meetingsArray.add(meetingObj);
            }
            String meetingsJson = gson.toJson(meetingsArray);
            
            // Set response content type
            response.setContentType("application/json");
            // Get PrintWriter object
            PrintWriter out = response.getWriter();
            // Send JSON response
            out.println(meetingsJson);
            
            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error fetching meetings from database.");
        }
    }
}
