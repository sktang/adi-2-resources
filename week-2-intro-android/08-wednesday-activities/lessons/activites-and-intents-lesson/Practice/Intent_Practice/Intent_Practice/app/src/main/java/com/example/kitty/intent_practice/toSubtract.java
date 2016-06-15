package com.example.kitty.intent_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class toSubtract extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_subtract);

        TextView textView = (TextView) findViewById(R.id.subSum);
        Intent recievedIntent = getIntent();
        int finalSum = recievedIntent.getIntExtra("Sum", -1);
        textView.setText(finalSum+"");
    }
}
