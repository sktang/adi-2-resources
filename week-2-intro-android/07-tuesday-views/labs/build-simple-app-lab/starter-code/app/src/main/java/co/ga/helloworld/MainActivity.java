package co.ga.helloworld;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button blueBotton = (Button) findViewById(R.id.blueButton);
        Button pinkBotton = (Button) findViewById(R.id.pinkButton);
        Button purpleBotton = (Button) findViewById(R.id.purpleButton);
        Button greenBotton = (Button) findViewById(R.id.greenButton);


        // setting blue button
        View.OnClickListener blueClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView);
                int blue = ContextCompat.getColor(MainActivity.this, R.color.blue);
                textView.setTextColor(blue);
                textView.setText("Blue");
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.layout);
                myLayout.setBackgroundColor(Color.YELLOW);
            }
        };

        blueBotton.setOnClickListener(blueClicker);


        // setting pink button
        View.OnClickListener pinkClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView);
                int pink = ContextCompat.getColor(MainActivity.this, R.color.pink);
                textView.setTextColor(pink);
                textView.setText("Pink");
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.layout);
                myLayout.setBackgroundColor(Color.GREEN);
            }
        };

        pinkBotton.setOnClickListener(pinkClicker);


        // setting green button
        View.OnClickListener greenClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView);
                int green = ContextCompat.getColor(MainActivity.this, R.color.green);
                textView.setTextColor(green);
                textView.setText("Green");
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.layout);
                myLayout.setBackgroundColor(Color.MAGENTA);
            }
        };

        greenBotton.setOnClickListener(greenClicker);


        // setting purple button
        View.OnClickListener purpleClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.textView);
                int purple = ContextCompat.getColor(MainActivity.this, R.color.purple);
                textView.setTextColor(purple);
                textView.setText("Purple");
                RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.layout);
                myLayout.setBackgroundColor(Color.LTGRAY);
            }
        };

        purpleBotton.setOnClickListener(purpleClicker);

        /*
        // clicking on text
        TextView text = (TextView) findViewById(R.id.textView);
        View.OnClickListener textClicker = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                

            }
        };
        text.setOnClickListener(textClicker);
        */

    }
}
