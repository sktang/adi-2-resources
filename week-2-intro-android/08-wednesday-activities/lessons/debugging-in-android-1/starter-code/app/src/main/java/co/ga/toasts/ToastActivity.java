package co.ga.toasts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);


        Button buyBotton = (Button) findViewById(R.id.buyButton);
        Button saveBotton = (Button) findViewById(R.id.saveButton);
        Button sendBotton = (Button) findViewById(R.id.sendButton);
        Button likeBotton = (Button) findViewById(R.id.likeButton);


        // toast message for buy button
        View.OnClickListener buyClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToastActivity.this, "You bought something!", Toast.LENGTH_SHORT).show();
            }
        };

        buyBotton.setOnClickListener(buyClicker);


        // toast message for save button
        View.OnClickListener saveClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToastActivity.this, "You saved something!", Toast.LENGTH_SHORT).show();
            }
        };

        saveBotton.setOnClickListener(saveClicker);


        // toast message for send button
        View.OnClickListener sendClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToastActivity.this, "You sent something!", Toast.LENGTH_SHORT).show();
            }
        };

        sendBotton.setOnClickListener(sendClicker);


        // toast message for like button
        View.OnClickListener likeClicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToastActivity.this, "You liked something!", Toast.LENGTH_SHORT).show();
            }
        };

        likeBotton.setOnClickListener(likeClicker);


    }
}
