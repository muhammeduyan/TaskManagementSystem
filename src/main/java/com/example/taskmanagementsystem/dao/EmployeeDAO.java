package com.example.taskmanagementsystem.dao;

import com.example.taskmanagementsystem.model.Employee;
import com.example.taskmanagementsystem.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EmployeeDAO {

    // 1. TÜM ÇALIŞANLARI GETİR (SELECT *)
    public ObservableList<Employee> findAll() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Veritabanından gelen her satırı bir Employee nesnesine çeviriyoruz
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department")
                );
                employeeList.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
        return employeeList;
    }

    // 2. ID İLE ÇALIŞAN GETİR (SELECT WHERE id=?)
    public Employee findById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee emp = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // Soru işareti yerine ID'yi koyar
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department")
                );
            }
        } catch (SQLException e) {
            System.err.println("Arama hatası: " + e.getMessage());
        }
        return emp;
    }

    // 3. YENİ ÇALIŞAN EKLE (INSERT)
    public void save(Employee emp) {
        String sql = "INSERT INTO employees (name, department) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getDepartment());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Kaydetme hatası: " + e.getMessage());
        }
    }

    // 4. GÜNCELLE (UPDATE)
    public void update(Employee emp) {
        String sql = "UPDATE employees SET name = ?, department = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getDepartment());
            pstmt.setInt(3, emp.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Güncelleme hatası: " + e.getMessage());
        }
    }

    // 5. SİL (DELETE)
    public void delete(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Silme hatası: " + e.getMessage());
        }
    }
}
