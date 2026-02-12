package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.dao.EmployeeDAO;
import com.example.taskmanagementsystem.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EmployeeController {

    // FXML dosyasındaki elemanları buraya bağlıyoruz
    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtDepartment;

    // İş mantığı sınıfımızı çağırıyoruz
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    // 1. FETCH (GETİR) BUTONU
    @FXML
    void onFetch(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            Employee emp = employeeDAO.findById(id);

            if (emp != null) {
                txtName.setText(emp.getName());
                txtDepartment.setText(emp.getDepartment());
            } else {
                showAlert("Uyarı", "Kayıt Bulunamadı", "Bu ID'ye ait çalışan yok.");
            }
        } catch (NumberFormatException e) {
            showAlert("Hata", "Geçersiz ID", "Lütfen ID alanına sayı girin.");
        }
    }

    // 2. SAVE (KAYDET) BUTONU
    @FXML
    void onSave(ActionEvent event) {
        String name = txtName.getText();
        String department = txtDepartment.getText();

        if (name.isEmpty() || department.isEmpty()) {
            showAlert("Hata", "Eksik Bilgi", "İsim ve Departman boş olamaz!");
            return;
        }

        Employee emp = new Employee(0, name, department); // ID otomatik artar (Serial)
        employeeDAO.save(emp);
        showAlert("Başarılı", "Kayıt Eklendi", "Yeni çalışan başarıyla eklendi.");
        clearFields();
    }

    // 3. UPDATE (GÜNCELLE) BUTONU
    @FXML
    void onUpdate(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String department = txtDepartment.getText();

            Employee emp = new Employee(id, name, department);
            employeeDAO.update(emp);
            showAlert("Başarılı", "Güncellendi", "Çalışan bilgileri güncellendi.");
        } catch (NumberFormatException e) {
            showAlert("Hata", "ID Hatası", "Lütfen geçerli bir ID girin.");
        }
    }

    // 4. DELETE (SİL) BUTONU
    @FXML
    void onDelete(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            employeeDAO.delete(id);
            showAlert("Bilgi", "Silindi", "Çalışan kaydı silindi.");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Hata", "ID Hatası", "Silmek için geçerli bir ID girin.");
        }
    }

    // 5. CLOSE (KAPAT) BUTONU
    @FXML
    void onClose(ActionEvent event) {
        System.exit(0);
    }

    // Yardımcı Metod: Alanları temizle
    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtDepartment.clear();
    }

    // Yardımcı Metod: Ekrana uyarı bas
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
