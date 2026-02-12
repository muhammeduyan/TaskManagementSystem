package com.example.taskmanagementsystem.model;

public class Employee {
    private int id;
    private String name;
    private String department;

    // Boş Constructor (Zorunlu)
    public Employee() {}

    // Dolu Constructor (Veri eklerken lazım)
    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getter ve Setter Metodları (Bunlar olmazsa JavaFX veriyi okuyamaz)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
