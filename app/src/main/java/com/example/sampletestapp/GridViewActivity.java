package com.example.sampletestapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewActivity extends AppCompatActivity {
    GridView GrdLytGrdVw1;
    static final String[] alphabets = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y",
            "Z" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        Log.e( getString(R.string.TAG4), "ThirdActivity OnCreate Called" );

        GrdLytGrdVw1 = (GridView) findViewById(R.id.GrdLytGrdVw1);

        Log.e( getString(R.string.TAG4), "ThirdActivity 1" );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alphabets );
        GrdLytGrdVw1.setAdapter(adapter);
        Log.e( getString(R.string.TAG4), "ThirdActivity 2" );

        GrdLytGrdVw1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e( getString(R.string.TAG4), "ThirdActivity 3" );
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}