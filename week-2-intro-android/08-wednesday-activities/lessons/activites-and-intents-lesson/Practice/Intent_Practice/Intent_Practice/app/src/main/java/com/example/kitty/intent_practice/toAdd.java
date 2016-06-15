package com.example.kitty.intent_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class toAdd extends AppCompatActivity {

    //First Exercise
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_to_add);
//
//        TextView textView = (TextView) findViewById(R.id.addSum);
//        Intent recievedIntent = getIntent();
//        int finalSum = recievedIntent.getIntExtra("Sum", -1);
//        textView.setText(finalSum+"");
//
//    }


    //Second Exercise
    Button calcButton;
    EditText firstNumT, secondNumT;
    Integer num1 = 0;
    Integer num2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_add);

        calcButton = (Button)findViewById(R.id.calculate);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                firstNumT = (EditText)findViewById(R.id.num1);
                secondNumT = (EditText)findViewById(R.id.num2);
                String num1S = firstNumT.getText().toString();
                String num2S = secondNumT.getText().toString();
                num1 = Integer.parseInt(num1S);
                num2 = Integer.parseInt(num2S);
                int sum = (int) num1 + num2;
                resultIntent.putExtra("sum", sum);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }


}
