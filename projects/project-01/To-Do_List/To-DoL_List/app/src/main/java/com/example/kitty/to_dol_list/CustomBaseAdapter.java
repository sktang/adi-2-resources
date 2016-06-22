package com.example.kitty.to_dol_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kitty on 6/22/16.
 */
public class CustomBaseAdapter extends BaseAdapter {

    private ArrayList<Task> data;
    private Context context;
    private ViewHolder viewHolder;

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
        } else viewHolder.checkBox.setChecked(false);

        // action if checkbox is checked
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    currentTask.setDone();
                } else currentTask.setUnDone();
            }
        });

        viewHolder.taskName.setText(currentTask.getTaskname());
        viewHolder.taskDescription.setText(currentTask.getTaskDescription());

        viewHolder.removeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(currentTask);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
