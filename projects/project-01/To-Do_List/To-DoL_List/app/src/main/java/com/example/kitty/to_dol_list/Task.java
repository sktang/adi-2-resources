package com.example.kitty.to_dol_list;

/**
 * Created by kitty on 6/21/16.
 */
public class Task {

    private String taskName;
    private String taskDescription;
    private boolean isDone;

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isDone = false;
    }

    public String getTaskname() {
        return taskName;
    }

    public void setTaskname(String taskname) {
        this.taskName = taskname;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }

    public void setUnDone() {
        isDone = false;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
