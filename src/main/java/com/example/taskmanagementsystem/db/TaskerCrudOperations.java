package com.example.taskmanagementsystem.db;

import com.example.taskmanagementsystem.dto.Tasker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.taskmanagementsystem.util.DatabaseConnection;

@SuppressWarnings({"SqlNoDataSourceInspection", "UnusedReturnValue"})
public class TaskerCrudOperations {
    private static final Logger LOGGER = Logger.getLogger(TaskerCrudOperations.class.getName());

    // Atama ekleme (Insert)
    public int insertTasker(Tasker tasker) {
        String query = "INSERT INTO Taskers (employee_id, task_id, task_date, task_time) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tasker.getEmployeeId());
            statement.setInt(2, tasker.getTaskId());
            statement.setDate(3, tasker.getTaskDate());
            statement.setTime(4, tasker.getTaskTime());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Belirli bir çalışanın görevlerini getirme (Fetch)
    public List<Tasker> getTaskersByEmployeeId(int employeeId) {
        List<Tasker> taskers = new ArrayList<>();
        String query = "SELECT * FROM Taskers WHERE employee_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tasker tasker = new Tasker();
                    tasker.setEmployeeId(resultSet.getInt("employee_id"));
                    tasker.setTaskId(resultSet.getInt("task_id"));
                    tasker.setTaskDate(resultSet.getDate("task_date"));
                    tasker.setTaskTime(resultSet.getTime("task_time"));
                    taskers.add(tasker);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch taskers by employee id: " + employeeId, e);
        }
        return taskers;
    }

    // Atamayı silme (Delete)
    public int deleteTasker(int employeeId, int taskId) {
        String query = "DELETE FROM Taskers WHERE employee_id = ? AND task_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.setInt(2, taskId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
