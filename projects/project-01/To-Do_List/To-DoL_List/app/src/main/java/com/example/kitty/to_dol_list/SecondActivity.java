package com.example.kitty.to_dol_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public static final String RETURN_SERIALIZABLE_KEY = "ReturnSerializableKey";

    private EditText listName;
    private SubList indList;
    private ListView subList;
    private FloatingActionButton addTask;
    private ImageButton backButton;

    private CustomBaseAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        indList = (SubList) getIntent().getSerializableExtra(MainActivity.LIST_SERIALIZABLE_KEY);

        listName = (EditText) findViewById(R.id.sublist_name);
        subList = (ListView) findViewById(R.id.sublist_list);
        backButton = (ImageButton) findViewById(R.id.back_button);
        addTask = (FloatingActionButton) findViewById(R.id.add_new_task);

        listName.setText(indList.getListName());

        listAdapter = new CustomBaseAdapter(indList.getItems(), SecondActivity.this);

        subList.setAdapter(listAdapter);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indList.addItem(new Task("", ""));
                listAdapter.notifyDataSetChanged();
            }
        });

        // save data after user input
        listName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    return;
                }
                indList.setListName(listName.getText().toString());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listName.getText().toString().isEmpty()) {
                    listName.setError("Please enter a list name");
                } else {
                    indList.setListName(listName.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(RETURN_SERIALIZABLE_KEY, indList);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(listAdapter.context, "List saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
