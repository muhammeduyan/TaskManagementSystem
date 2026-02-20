package com.example.taskmanagementsystem.dto;

import java.sql.Date;
import java.sql.Time;

@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
public class Tasker {
    private int employeeId;
    private int taskId;
    private Date taskDate;
    private Time taskTime;

    public Tasker() {}

    public Tasker(int employeeId, int taskId, Date taskDate, Time taskTime) {
        this.employeeId = employeeId;
        this.taskId = taskId;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public Date getTaskDate() { return taskDate; }
    public void setTaskDate(Date taskDate) { this.taskDate = taskDate; }

    public Time getTaskTime() { return taskTime; }
    public void setTaskTime(Time taskTime) { this.taskTime = taskTime; }
}
