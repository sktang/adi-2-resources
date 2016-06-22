package com.example.kitty.to_dol_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kitty on 6/22/16.
 */
public class CustomBaseAdapterMaster extends BaseAdapter {

    private ArrayList<SubList> data;
    private Context context;
    private ViewHolderMaster viewHolderMaster;

    public CustomBaseAdapterMaster(ArrayList<SubList> subList, Context context) {
        this.data = subList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.master_list_item, parent, false);
            viewHolderMaster = new ViewHolderMaster(convertView);
            convertView.setTag(viewHolderMaster);
        } else {
            viewHolderMaster = (ViewHolderMaster) convertView.getTag();
        }

        final SubList currentSubList = data.get(position);

        viewHolderMaster.listName.setText(currentSubList.getListName());

        viewHolderMaster.removeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(currentSubList);
            }
        });

        return convertView;
    }
}
