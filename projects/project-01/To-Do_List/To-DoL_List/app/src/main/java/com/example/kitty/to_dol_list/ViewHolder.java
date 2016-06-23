package com.example.kitty.to_dol_list;

import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
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

    public ViewHolder(View itemLayout) {
        this.checkBox = (CheckBox) itemLayout.findViewById(R.id.checkbox);
        this.taskName = (EditText) itemLayout.findViewById(R.id.task_name);
        this.taskDescription = (EditText) itemLayout.findViewById(R.id.task_description);
        this.removeListButton = (Button) itemLayout.findViewById(R.id.remove_task_button);
    }

    @Override
    public String toString() {
        return TextUtils.join(",", new String[] {taskName.getText().toString(), taskDescription.getText().toString()});
    }
}
