package com.example.kitty.to_dol_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kitty on 6/22/16.
 */
public class SecondActivity extends AppCompatActivity {

    public static final String LIST_SERIALIZABLE_KEY = "ListSerializableKey";

    private TextView listName;
    private SubList indList;
    private ListView subList;
    private FloatingActionButton addTask;
    private int mRequestCode;

    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        mRequestCode = getIntent().getIntExtra(MainActivity.REQUEST_CODE, -1);

        listName = (TextView) findViewById(R.id.sublist_name);
        subList = (ListView) findViewById(R.id.sublist_list);

        adapter = new ArrayAdapter<Task>(SecondActivity.this, R.layout.task_list_item, indList.getItems());

        subList.setAdapter(adapter);



    }
}
