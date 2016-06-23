package com.example.kitty.to_dol_list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kitty on 6/22/16.
 */
public class CustomBaseAdapter extends BaseAdapter {

    private ArrayList<Task> data;
    Context context;

    public CustomBaseAdapter(ArrayList<Task> taskList, Context context) {
        this.data = taskList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Task currentTask = data.get(position);

        // checkbox checked if boolean isDone == true
        if(currentTask.isDone()) {
            viewHolder.checkBox.setChecked(true);
            viewHolder.taskName.setTextColor(Color.GRAY);
            viewHolder.taskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.taskDescription.setTextColor(Color.GRAY);
            viewHolder.taskDescription.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.checkBox.setChecked(false);
            viewHolder.taskName.setTextColor(Color.BLACK);
            viewHolder.taskName.setPaintFlags(0);
            viewHolder.taskDescription.setTextColor(Color.BLACK);
            viewHolder.taskDescription.setPaintFlags(0);
        }

        // action if checkbox is checked
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    currentTask.setDone();
                    viewHolder.checkBox.setChecked(true);
                    viewHolder.taskName.setTextColor(Color.GRAY);
                    viewHolder.taskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.taskDescription.setTextColor(Color.GRAY);
                    viewHolder.taskDescription.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    currentTask.setUnDone();
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.taskName.setTextColor(Color.BLACK);
                    viewHolder.taskName.setPaintFlags(0);
                    viewHolder.taskDescription.setTextColor(Color.BLACK);
                    viewHolder.taskDescription.setPaintFlags(0);
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.taskName.setText(currentTask.getTaskname());
        viewHolder.taskName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }
                currentTask.setTaskname(viewHolder.taskName.getText().toString());
            }
        });

        viewHolder.taskDescription.setText(currentTask.getTaskDescription());
        viewHolder.taskDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    return;
                }
                currentTask.setTaskDescription(viewHolder.taskDescription.getText().toString());
            }
        });

        viewHolder.removeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(currentTask);
                Toast.makeText(context, "Task removed", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}