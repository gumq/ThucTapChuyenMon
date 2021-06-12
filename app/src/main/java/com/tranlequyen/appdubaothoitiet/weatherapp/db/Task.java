package com.tranlequyen.appdubaothoitiet.weatherapp.db;

public class Task {
    private int taskId;
    private String taskName;
    private byte[] photo;

    public Task(int taskId, String taskName, byte[] photo) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.photo = photo;

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }
}
