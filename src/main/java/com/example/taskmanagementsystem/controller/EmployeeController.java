package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.db.EmployeeCrudOperations;
import com.example.taskmanagementsystem.dto.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Optional;

public class EmployeeController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtDepartment;

    private final EmployeeCrudOperations dbOps = new EmployeeCrudOperations();

    @FXML
    void getEmployee() {
        int id = Integer.parseInt(txtId.getText());
        Optional<Employee> emp = dbOps.getEmployeeById(id);

        if (emp.isPresent()) {
            txtName.setText(emp.get().getName());
            txtDepartment.setText(emp.get().getDepartment());
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Employee with id " + id + " not found");
        }
    }

    @FXML
    void saveEmployee() {
        Employee emp = new Employee(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtDepartment.getText()
        );
        int result = dbOps.insertEmployee(emp);
        if (result == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "There is another employee with id: " + emp.getId());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Employee saved successfully.");
        }
    }

    @FXML
    void updateEmployee() {
        Employee emp = new Employee(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtDepartment.getText()
        );
        dbOps.updateEmployee(emp);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated.");
    }

    @FXML
    void deleteEmployee() {
        int id = Integer.parseInt(txtId.getText());
        dbOps.deleteEmployee(id);
        clearEmployee();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted.");
    }

    @FXML
    void clearEmployee() {
        txtId.clear();
        txtName.clear();
        txtDepartment.clear();
    }

    @FXML
    void close() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
