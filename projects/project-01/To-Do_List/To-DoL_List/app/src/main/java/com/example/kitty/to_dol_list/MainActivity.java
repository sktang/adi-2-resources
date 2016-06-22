package com.example.kitty.to_dol_list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String REQUEST_CODE = "rqCode";
    public static final int CURR_LIST = 123;
    MasterList toDoList;
    ListView mastList;
    FloatingActionButton addNewList;
    ArrayAdapter<SubList> masterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = toDoList.getInstance();

        masterAdapter = new ArrayAdapter<SubList>(MainActivity.this, R.layout.master_list_item, toDoList.getSubList());

        mastList = (ListView) findViewById(R.id.master_list);
        mastList.setAdapter(masterAdapter);

        addNewList = (FloatingActionButton) findViewById(R.id.add_new_list);

        /*
        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            SubList updatedList = (SubList) data.getSerializableExtra(SecondActivity.LIST_SERIALIZABLE_KEY);
            toDoList.addSubList(updatedList);
            masterAdapter.notifyDataSetChanged();
        }
    }
}
