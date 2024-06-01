package com.bookclubtracker.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewClubsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "BookClub";

        // SQL query to fetch book club data
        String query = "SELECT club_name, description, created_by, created_at FROM public.book_clubs";

        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            // Create statement
            Statement statement = connection.createStatement();
            // Execute query
            ResultSet resultSet = statement.executeQuery(query);

            // Set response content type
            response.setContentType("text/html");
            // Get PrintWriter object
            PrintWriter out = response.getWriter();

            // Send data to HTML page
            out.println("<html><body>");
            out.println("<h2>Available Book Clubs</h2>");
            out.println("<table border='1'><tr><th>Club Name</th><th>Description</th><th>Creator</th><th>Creation Date</th></tr>");
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("club_name") + "</td>");
                out.println("<td>" + resultSet.getString("description") + "</td>");
                out.println("<td>" + resultSet.getString("creator") + "</td>");
                out.println("<td>" + resultSet.getDate("creation_date") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            // Close connections
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
