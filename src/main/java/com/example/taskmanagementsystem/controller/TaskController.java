package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.db.TaskCrudOperations;
import com.example.taskmanagementsystem.dto.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Optional;

public class TaskController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitle;
    @FXML private TextField txtDescription;

    private final TaskCrudOperations dbOps = new TaskCrudOperations();

    @FXML
    void getTask() {
        int id = Integer.parseInt(txtId.getText());
        Optional<Task> task = dbOps.getTaskById(id);

        if (task.isPresent()) {
            txtTitle.setText(task.get().getTitle());
            txtDescription.setText(task.get().getDescription());
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Task with id " + id + " not found");
        }
    }

    @FXML
    void saveTask() {
        Task task = new Task(
                Integer.parseInt(txtId.getText()),
                txtTitle.getText(),
                txtDescription.getText()
        );
        int result = dbOps.insertTask(task);
        if (result == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "There is another task with id: " + task.getId());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Task saved.");
        }
    }

    @FXML
    void updateTask() {
        Task task = new Task(
                Integer.parseInt(txtId.getText()),
                txtTitle.getText(),
                txtDescription.getText()
        );
        dbOps.updateTask(task);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Task updated.");
    }

    @FXML
    void deleteTask() {
        int id = Integer.parseInt(txtId.getText());
        dbOps.deleteTask(id);
        clearTask();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Task deleted.");
    }

    @FXML
    void clearTask() {
        txtId.clear();
        txtTitle.clear();
        txtDescription.clear();
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
