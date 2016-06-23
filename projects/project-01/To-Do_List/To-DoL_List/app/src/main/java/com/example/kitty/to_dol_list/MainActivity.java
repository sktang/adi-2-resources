package com.example.kitty.to_dol_list;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LIST_SERIALIZABLE_KEY = "ListSerializableKey";

    //public static final String REQUEST_CODE = "rqCode";
    public static final int CURR_LIST = 123;
    MasterList toDoList;
    ListView masterList;
    FloatingActionButton addNewList;
    CustomBaseAdapterMaster masterAdapter;
    private int listNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = MasterList.getInstance();

        Task task1 = new Task("Test Task", "Task description");
        task1.setDone();
        SubList list1 = new SubList("list 1");
        list1.addItem(task1);
        toDoList.addSubList(list1);

        masterAdapter = new CustomBaseAdapterMaster(toDoList.getSubLists(), MainActivity.this);

        System.out.println(toDoList.getSubList(0).getListName());

        masterList = (ListView) findViewById(R.id.master_list);
        masterList.setAdapter(masterAdapter);

        masterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listNum = position;
                //Toast.makeText(MainActivity.this, "Clicked on a list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                SubList currList = toDoList.getSubList(position);
                intent.putExtra(LIST_SERIALIZABLE_KEY, currList);
                startActivityForResult(intent, CURR_LIST);
            }
        });

        addNewList = (FloatingActionButton) findViewById(R.id.add_new_list);

        addNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoList.addSubList(new SubList(" "));
                Toast.makeText(MainActivity.this, "Added new list", Toast.LENGTH_SHORT).show();
                masterAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            SubList updatedList = (SubList) data.getSerializableExtra(SecondActivity.RETURN_SERIALIZABLE_KEY);
            toDoList.setSubList(listNum, updatedList);
            masterAdapter.notifyDataSetChanged();
        }
    }
}
