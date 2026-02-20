package com.example.taskmanagementsystem.db;

import com.example.taskmanagementsystem.dto.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.taskmanagementsystem.util.DatabaseConnection;

@SuppressWarnings({"SqlNoDataSourceInspection", "UnusedReturnValue"})
public class TaskCrudOperations {
    private static final Logger LOGGER = Logger.getLogger(TaskCrudOperations.class.getName());


    // Get a task by id
    public Optional<Task> getTaskById(int id) {
        String query = "SELECT * FROM Tasks WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setTitle(resultSet.getString("title"));
                    task.setDescription(resultSet.getString("description"));
                    return Optional.of(task);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch task by id: " + id, e);
        }
        return Optional.empty();
    }

    // Insert a task by id (Manuel ID kontrolü ile)
    public int insertTask(Task task) {
        if (getTaskById(task.getId()).isPresent()) {
            return -1; // UI'da ID çakışma hatası verebilmek için
        }
        String query = "INSERT INTO Tasks (id, title, description) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getDescription());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update a task by id
    public int updateTask(Task task) {
        if (!getTaskById(task.getId()).isPresent()) {
            return 0;
        }
        String query = "UPDATE Tasks SET title = ?, description = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete a task by id
    public int deleteTask(int id) {
        String query = "DELETE FROM Tasks WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
