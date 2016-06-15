package com.example.kitty.intent_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //First Exercise
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button add = (Button) findViewById(R.id.add);
//        assert add != null;
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText text1 = (EditText) findViewById(R.id.num1);
//                EditText text2 = (EditText) findViewById(R.id.num2);
//                String num1text = text1.getText().toString();
//                String num2text = text2.getText().toString();
//
//                //convert string to int
//                Integer num1 = Integer.parseInt(num1text);
//                Integer num2 = Integer.parseInt(num2text);
//                int sum = (int) num1 + num2;
//                Intent intent = new Intent(MainActivity.this, toAdd.class);
//                intent.putExtra("Sum", sum);
//                startActivity(intent);
//            }
//        });
//
//
//        Button subtract = (Button) findViewById(R.id.subtract);
//        assert subtract != null;
//        subtract.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText text1 = (EditText) findViewById(R.id.num1);
//                EditText text2 = (EditText) findViewById(R.id.num2);
//                String num1text = text1.getText().toString();
//                String num2text = text2.getText().toString();
//
//                //convert string to int
//                Integer num1 = Integer.parseInt(num1text);
//                Integer num2 = Integer.parseInt(num2text);
//                int sum = (int) num1 - num2;
//                Intent intent = new Intent(MainActivity.this, toSubtract.class);
//                intent.putExtra("Sum", sum);
//                startActivity(intent);
//            }
//        });


        //Second exercise
        private TextView sumText;
        private Button addButton;

        public static int REQUEST_CODE = 30;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            sumText = (TextView) findViewById(R.id.sum);

            addButton = (Button) findViewById(R.id.add);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, toAdd.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE) {
                // Make sure the request was successful...
                if (resultCode == RESULT_OK) {
                    int sum = data.getIntExtra("sum", -1);
                    sumText.setText(sum+" ");
                }
            }

        }


    }

