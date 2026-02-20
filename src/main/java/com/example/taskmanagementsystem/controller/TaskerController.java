package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.db.TaskerCrudOperations;
import com.example.taskmanagementsystem.dto.Tasker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class TaskerController {

    @FXML private TextField txtEmployeeId;
    @FXML private TextField txtTaskId;
    @FXML private TextField txtTaskDate; // Format: YYYY-MM-DD
    @FXML private TextField txtTaskTime; // Format: HH:MM:SS

    private final TaskerCrudOperations dbOps = new TaskerCrudOperations();

    @FXML
    void saveTasker() {
        try {
            Tasker tasker = new Tasker(
                    Integer.parseInt(txtEmployeeId.getText()),
                    Integer.parseInt(txtTaskId.getText()),
                    Date.valueOf(txtTaskDate.getText()),
                    Time.valueOf(txtTaskTime.getText())
            );
            dbOps.insertTasker(tasker);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Task assigned to employee.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Format Error", "Date must be YYYY-MM-DD and Time HH:MM:SS");
        }
    }

    @FXML
    void getTaskers() {
        // Belirli bir çalışanın görev atamalarını logluyoruz (Basit tutmak adına)
        int empId = Integer.parseInt(txtEmployeeId.getText());
        List<Tasker> taskers = dbOps.getTaskersByEmployeeId(empId);

        if (taskers.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Info", "No tasks found for Employee ID: " + empId);
        } else {
            // İlk bulunan görevi text field'lara yazdıralım
            Tasker firstTask = taskers.get(0);
            txtTaskId.setText(String.valueOf(firstTask.getTaskId()));
            txtTaskDate.setText(firstTask.getTaskDate().toString());
            txtTaskTime.setText(firstTask.getTaskTime().toString());
            showAlert(Alert.AlertType.INFORMATION, "Found", taskers.size() + " task(s) found. Showing the first one.");
        }
    }

    @FXML
    void deleteTasker() {
        int empId = Integer.parseInt(txtEmployeeId.getText());
        int taskId = Integer.parseInt(txtTaskId.getText());
        dbOps.deleteTasker(empId, taskId);
        clearTasker();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Task assignment removed.");
    }

    @FXML
    void clearTasker() {
        txtEmployeeId.clear();
        txtTaskId.clear();
        txtTaskDate.clear();
        txtTaskTime.clear();
    }

    @FXML
    void close() {
        Stage stage = (Stage) txtEmployeeId.getScene().getWindow();
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
