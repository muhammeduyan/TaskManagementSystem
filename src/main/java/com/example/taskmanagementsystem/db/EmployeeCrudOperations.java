package com.example.taskmanagementsystem.db;

import com.example.taskmanagementsystem.dto.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.taskmanagementsystem.util.DatabaseConnection;

@SuppressWarnings({"SqlNoDataSourceInspection", "UnusedReturnValue"})
public class EmployeeCrudOperations {
    private static final Logger LOGGER = Logger.getLogger(EmployeeCrudOperations.class.getName());

    // Get an employee by id
    public Optional<Employee> getEmployeeById(int id) {
        String query = "SELECT * FROM Employees WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setDepartment(resultSet.getString("department"));
                    return Optional.of(employee);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch employee by id: " + id, e);
        }
        return Optional.empty();
    }

    // Insert an employee (Hocanın manuel ID kontrol mantığıyla)
    public int insertEmployee(Employee emp) {
        if (getEmployeeById(emp.getId()).isPresent()) {
            return -1; // UI tarafında "There another customer with id: X" hatası vermek için
        }
        String query = "INSERT INTO Employees (id, name, department) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, emp.getId());
            statement.setString(2, emp.getName());
            statement.setString(3, emp.getDepartment());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update an employee
    public int updateEmployee(Employee emp) {
        if (!getEmployeeById(emp.getId()).isPresent()) {
            return 0;
        }
        String query = "UPDATE Employees SET name = ?, department = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, emp.getName());
            statement.setString(2, emp.getDepartment());
            statement.setInt(3, emp.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete an employee
    public int deleteEmployee(int id) {
        String query = "DELETE FROM Employees WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
