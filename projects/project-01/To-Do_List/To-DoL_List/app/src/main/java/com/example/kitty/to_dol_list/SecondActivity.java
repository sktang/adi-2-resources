package com.example.kitty.to_dol_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by kitty on 6/22/16.
 */
public class SecondActivity extends AppCompatActivity {

    public static final String RETURN_SERIALIZABLE_KEY = "ReturnSerializableKey";

    private EditText listName;
    private SubList indList;
    private ListView subList;
    private FloatingActionButton addTask;
    private FloatingActionButton backButton;

    private CustomBaseAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        indList = (SubList) getIntent().getSerializableExtra(MainActivity.LIST_SERIALIZABLE_KEY);

        listName = (EditText) findViewById(R.id.sublist_name);
        subList = (ListView) findViewById(R.id.sublist_list);
        backButton = (FloatingActionButton) findViewById(R.id.back_button);
        addTask = (FloatingActionButton) findViewById(R.id.add_new_task);

        listAdapter = new CustomBaseAdapter(indList.getItems(), SecondActivity.this);

        subList.setAdapter(listAdapter);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indList.addItem(new Task(" ", " "));
                listAdapter.notifyDataSetChanged();
            }
        });



    }
}
