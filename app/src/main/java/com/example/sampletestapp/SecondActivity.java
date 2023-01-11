package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView SATextView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e( getString(R.string.TAG), "SecondActivity OnCreate Called" );
        SATextView1 = ( TextView)  findViewById(R.id.SecondtextView1);
        SATextView1.setText("Poora Puka");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(SecondActivity.this, "SA onTouch Called", Toast.LENGTH_LONG).show();
        Log.e( getString(R.string.TAG), "SecondActivity onTouch Called" );
        SATextView1.setText("Madda Guddu");
        return super.onTouchEvent(event);
    }
}