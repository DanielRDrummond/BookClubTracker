package com.bookclubtracker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bookclubtracker.model.User;
import com.bookclubtracker.model.BookClub;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/BookClub";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "BookClub";

    public static User getUserByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setHashedPassword(resultSet.getString("hashed_password"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean createBookClub(BookClub bookClub) {
        // Logic to save the book club to the database
        // Return true if successful, false otherwise
        boolean success = false;
        // Your logic to save the book club to the database goes here
        // Set success to true if the operation is successful
        return success;
    }
}
