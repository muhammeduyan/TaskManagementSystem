module com.example.taskmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;      // Veritabanı için şart

    // Model sınıfların (Employee, Task) veritabanı veya TableView tarafından okunabilmesi için:
    opens com.example.taskmanagementsystem.db to javafx.base;

    // FXML dosyasının Controller sınıfına erişebilmesi için:
    opens com.example.taskmanagementsystem.controller to javafx.fxml;

    // Ana uygulamanın (App.java) çalışması için:
    opens com.example.taskmanagementsystem to javafx.fxml;

    exports com.example.taskmanagementsystem;
    opens com.example.taskmanagementsystem.dto to javafx.base;
}
