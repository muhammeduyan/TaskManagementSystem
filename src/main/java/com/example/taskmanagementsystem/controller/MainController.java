package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    // ÇALIŞAN EKRANINI AÇAN METOD
    @FXML
    void openEmployeeView(ActionEvent event) {
        openWindow("employee-view.fxml", "Employee Management");
    }

    // GÖREV EKRANINI AÇAN METOD
    @FXML
    void openTaskView(ActionEvent event) {
        openWindow("task-view.fxml", "Task Management");
    }

    // YARDIMCI METOD (Kod tekrarını önlemek için)
    private void openWindow(String fxmlFileName, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load());

            // Yeni bir pencere (Stage) oluşturuyoruz
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Pencere açılırken hata oluştu: " + fxmlFileName);
        }
    }
}