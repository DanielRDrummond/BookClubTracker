package com.bookclubtracker.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppInitializer implements ServletContextListener {
    // JDBC URL of your PostgreSQL server
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bookclub";
    // Username and password for your PostgreSQL server
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "BookClub";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Book Club Tracker application...");

        // Initialize database connection
        Connection connection = initializeDatabase();
        if (connection != null) {
            System.out.println("Database connection established successfully.");
            // You can store the connection in the servlet context if needed
            sce.getServletContext().setAttribute("DBConnection", connection);
        } else {
            System.err.println("Failed to establish database connection.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Book Club Tracker application...");
        // Close the database connection if it's stored in the context
        Connection connection = (Connection) sce.getServletContext().getAttribute("DBConnection");
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to initialize database connection
    private Connection initializeDatabase() {
        try {
            // Register the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            // Create connection
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
