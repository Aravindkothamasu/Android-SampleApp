package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
                int mnth, yr;

                mnth = idStatMnth.getValue();
                yr   = idStatYr.getValue();

                Log.e(getString(R.string.STATS), "MN/YY : "+mnth+"/"+yr);
                Toast.makeText(PurchaseStats.this, "MN/YY : "+mnth+"/"+yr, Toast.LENGTH_SHORT).show();

                idTxtView.setText("Processing : "+mnth+"/"+yr);
                resetPickerValues();
            }
        });
    }


    private void resetPickerValues() {
        idStatMnth.setValue(1);
        idStatYr.setValue(0);
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