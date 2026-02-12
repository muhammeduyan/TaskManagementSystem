package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.dao.TaskDAO;
import com.example.taskmanagementsystem.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TaskController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitle;
    @FXML private TextField txtDescription;

    private final TaskDAO taskDAO = new TaskDAO();

    @FXML
    void onFetch(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            Task task = taskDAO.findById(id);

            if (task != null) {
                txtTitle.setText(task.getTitle());
                txtDescription.setText(task.getDescription());
            } else {
                showAlert("Uyarı", "Bulunamadı", "Bu ID'ye ait görev yok.");
            }
        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçersiz ID", "Lütfen sayı girin.");
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        String title = txtTitle.getText();
        String desc = txtDescription.getText();

        if (title.isEmpty()) {
            showAlert("Hata", "Eksik Bilgi", "Başlık zorunludur!");
            return;
        }

        Task task = new Task(0, title, desc);
        taskDAO.save(task);
        showAlert("Başarılı", "Eklendi", "Görev başarıyla eklendi.");
        clearFields();
    }

    @FXML
    void onUpdate(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String title = txtTitle.getText();
            String desc = txtDescription.getText();

            Task task = new Task(id, title, desc);
            taskDAO.update(task);
            showAlert("Başarılı", "Güncellendi", "Görev güncellendi.");
        } catch (NumberFormatException e) {
            showAlert("Hata", "ID Hatası", "ID giriniz.");
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            taskDAO.delete(id);
            showAlert("Bilgi", "Silindi", "Görev silindi.");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Hata", "ID Hatası", "ID giriniz.");
        }
    }

    @FXML
    void onClose(ActionEvent event) {
        System.exit(0);
    }

    private void clearFields() {
        txtId.clear();
        txtTitle.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
