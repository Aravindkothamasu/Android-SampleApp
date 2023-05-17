package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseStats extends AppCompatActivity {
    private NumberPicker idStatMnth, idStatYr;
    private Button idStatGetData;
    private TextView idTxtView;
    private static final String MnthValues[] = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_stats);
        Log.e( getString(R.string.STATS), "onCreate Called");

        variableInit();

        idStatGetData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int month=0, yr=0;
                Pair<Integer, Integer> date;

                date  = readPickerValues();
                month = date.first;
                yr    = date.second;

                Log.e(getString(R.string.STATS), "TEST MN/YY : "+month+"/"+yr);
                Toast.makeText(PurchaseStats.this, "STAT : MN/YY : "+month+"/"+yr, Toast.LENGTH_SHORT).show();
                idTxtView.setText("Processing : "+month+"/"+yr);

                resetPickerValues();
            }
        });
    }

    private Pair<Integer, Integer> readPickerValues() {
        int month=0, yr=0;
        month = idStatMnth.getValue();
        yr    = idStatYr.getValue();
        return new Pair<Integer, Integer>(month, yr);
    }

    private void resetPickerValues() {
        idStatMnth.setValue(idStatMnth.getMinValue());
        idStatYr.setValue(idStatYr.getMinValue());
    }

    private void variableInit() {

        idStatMnth    = findViewById(R.id.idStatMnth);
        idStatYr      = findViewById(R.id.idStatYr);
        idStatGetData = findViewById(R.id.idStatGetData);
        idTxtView     = findViewById(R.id.idTxtView);

        idStatYr.setMinValue(2020);
        idStatYr.setMaxValue(2030);
        idStatYr.setWrapSelectorWheel(true);

        idStatMnth.setMinValue(1);
        idStatMnth.setMaxValue(12);
        idStatMnth.setWrapSelectorWheel(true);
        idStatMnth.setDisplayedValues(MnthValues);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e( getString(R.string.STATS), "onDestroy Called");
    }
}