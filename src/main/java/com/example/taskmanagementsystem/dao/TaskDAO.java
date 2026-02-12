package com.example.taskmanagementsystem.dao;

import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TaskDAO {

    // 1. TÜM GÖREVLERİ GETİR
    public ObservableList<Task> findAll() {
        ObservableList<Task> taskList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
                taskList.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Task listeleme hatası: " + e.getMessage());
        }
        return taskList;
    }

    // 2. ID İLE GÖREV BUL
    public Task findById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        Task task = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("Task arama hatası: " + e.getMessage());
        }
        return task;
    }

    // 3. KAYDET
    public void save(Task task) {
        String sql = "INSERT INTO tasks (title, description) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Task kaydetme hatası: " + e.getMessage());
        }
    }

    // 4. GÜNCELLE
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setInt(4, task.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Task güncelleme hatası: " + e.getMessage());
        }
    }

    // 5. SİL
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Task silme hatası: " + e.getMessage());
        }
    }
}