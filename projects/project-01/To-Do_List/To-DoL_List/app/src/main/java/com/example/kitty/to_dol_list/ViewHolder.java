package com.example.kitty.to_dol_list;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by kitty on 6/22/16.
 */
public class ViewHolder {

    CheckBox checkBox;
    EditText taskName;
    EditText taskDescription;
    Button removeListButton;
    FloatingActionButton addTask;

    public ViewHolder(View itemLayout) {
        this.checkBox = (CheckBox) itemLayout.findViewById(R.id.checkbox);
        this.taskName = (EditText) itemLayout.findViewById(R.id.task_name);
        this.taskDescription = (EditText) itemLayout.findViewById(R.id.task_description);
        this.removeListButton = (Button) itemLayout.findViewById(R.id.remove_task_button);
        this.addTask = (FloatingActionButton) itemLayout.findViewById(R.id.add_new_task);
    }
}
