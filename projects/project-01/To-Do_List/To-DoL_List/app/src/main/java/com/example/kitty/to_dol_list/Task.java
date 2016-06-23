package com.example.kitty.to_dol_list;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by kitty on 6/21/16.
 */
public class Task implements Serializable {

    private static final long serialVersionUID2 = 8526472295622776148L;

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

    @Override
    public String toString() {
        return TextUtils.join(",", new String[] {taskName, taskDescription});
    }
}
