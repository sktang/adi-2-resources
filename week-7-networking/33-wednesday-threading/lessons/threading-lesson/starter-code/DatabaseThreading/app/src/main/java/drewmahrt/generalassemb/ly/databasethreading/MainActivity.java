package drewmahrt.generalassemb.ly.databasethreading;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar)findViewById(R.id.progress);
        mTextView = (TextView)findViewById(R.id.text);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
                asyncTask.execute();
            }
        });
    }

    public void addDatabaseItems(){
        for (int i = 0; i < 1000; i++) {
            ExampleDBHelper.getInstance(getApplicationContext()).addName("John","Doe");
        }
    }

    private class DatabaseAsyncTask extends AsyncTask<String,Void,Integer> {

        private int count;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO: Change text to indicate items are being added
            mTextView.setText("Adding items to database...");

            //TODO: Make progress bar visible
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            //TODO: Add items to database
            addDatabaseItems();
            //TODO: Return number of items in the database
            count = ExampleDBHelper.getInstance(getApplicationContext()).getItemCount();
            return count;
        }

        @Override
        protected void onPostExecute(Integer count) {
            super.onPostExecute(count);
            //TODO: Hide progress bar
            mProgressBar.setVisibility(View.INVISIBLE);
            //TODO: Update text to indicate all items are added, and display the current count
            mTextView.setText("All items added to database! Current item count: "+count);
        }
    }

//    private class ExampleAsync extends AsyncTask<Double, String, Float> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Float aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected Float doInBackground(Double... params) {
//
//            publishProgress();
//            return null;
//        }
//    }

}
