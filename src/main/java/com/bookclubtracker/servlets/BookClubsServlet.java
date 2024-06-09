package com.bookclubtracker.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BookClubsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/bookclub";
        String dbUsername = "postgres";
        String dbPassword = "BookClub";
        
        // SQL query to fetch book club data
        String query = "SELECT * FROM book_clubs";
        
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            // Create statement
            Statement statement = connection.createStatement();
            // Execute query
            ResultSet resultSet = statement.executeQuery(query);
            
            // Convert result set to JSON using Gson library
            Gson gson = new Gson();
            JsonArray bookClubsArray = new JsonArray();
            while (resultSet.next()) {
                JsonObject bookClubObj = new JsonObject();
                bookClubObj.addProperty("name", resultSet.getString("club_name"));
                bookClubObj.addProperty("description", resultSet.getString("description"));
                bookClubObj.addProperty("creator", resultSet.getString("creator"));
                // Add more properties as needed
                bookClubsArray.add(bookClubObj);
            }
            String bookClubsJson = gson.toJson(bookClubsArray);
            
            // Set response content type
            response.setContentType("application/json");
            // Get PrintWriter object
            PrintWriter out = response.getWriter();
            // Send JSON response
            out.println(bookClubsJson);
            
            // Close connections
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error fetching book clubs from database.");
        }
    }
}
