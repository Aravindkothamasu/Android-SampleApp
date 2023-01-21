package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    Button RelLytBut1, RelLytBut2;
    TextView RelLytTxtVw1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.e( getString(R.string.TAG3), "onCreate Called" );
        Toast.makeText(this, "TAG3 ONSTART", Toast.LENGTH_SHORT).show();


        RelLytBut2 = ( Button) findViewById(R.id.RelLytBut2);
        RelLytBut1 = ( Button) findViewById(R.id.RelLytBut1);
        RelLytTxtVw1 = (TextView) findViewById(R.id.RelLytTxtVw1);

        RelLytBut1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Toast.makeText( ThirdActivity.this, "TAG3 BUT1", Toast.LENGTH_SHORT).show();
                RelLytTxtVw1.setText("BUT1 CLICK");
            }}
        );

        RelLytBut2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Toast.makeText( ThirdActivity.this, "TAG3 BUT2", Toast.LENGTH_SHORT).show();
                RelLytTxtVw1.setText("BUT2 CLICK");
            }}
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e( getString(R.string.TAG3), "onTouch Called" );
        Toast.makeText(this, "TAG3 ONTOUCH", Toast.LENGTH_SHORT).show();
        RelLytTxtVw1.setText("A3 ONTOUCH");
        return super.onTouchEvent(event);
    }
}