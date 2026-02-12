package com.example.taskmanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Veritabanı bilgilerin (PostgreSQL için)
    private static final String URL = "jdbc:postgresql://localhost:5432/task_db";
    private static final String USER = "postgres"; // Senin postgres kullanıcın (genelde postgres)
    private static final String PASSWORD = "123456"; // Postgres.app kullanıyorsan genelde boştur, değilse şifreni yaz

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası!");
            e.printStackTrace();
            return null;
        }
    }
}