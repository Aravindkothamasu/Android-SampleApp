package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;



public class PeriodicStats extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button   idPeriodBtn;
    private TextView idPeriodTxtVw;
    private NumberPicker idPeriodSrtMnth, idPeriodSrtYr;
    private NumberPicker idPeriodEndMnth, idPeriodEndYr;
    private static final String MnthValues[] = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec" };

    // Below Spinner related variables
    private Spinner idCategoryList;
    String selectedCategory;
    ArrayList<String> idCategoryAryLst = new ArrayList<String>();
    ArrayAdapter<String> idCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_stats);
        Log.e(getString(R.string.LOG_PERIODIC_STATS), "Periodic Stats Called");

        variableInit();
        resetPickerValues(idPeriodSrtMnth, idPeriodSrtYr);
        resetPickerValues(idPeriodEndMnth, idPeriodEndYr);


        idPeriodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentDate Start, End;
                Log.e(getString(R.string.LOG_PERIODIC_STATS), "PERIOD GET DATA CLICKED");
                Start = readPickerValues(idPeriodSrtMnth, idPeriodSrtYr);
                End   = readPickerValues(idPeriodEndMnth, idPeriodEndYr);

                Log.e( getString(R.string.LOG_PERIODIC_STATS), "CATEGORY "+selectedCategory+"\n");
                Log.e( getString(R.string.LOG_PERIODIC_STATS), "START MM/YY : "+ Start.Month+"/"+Start.Year+"\n");
                Log.e( getString(R.string.LOG_PERIODIC_STATS), "START MM/YY : "+ End.Month+"/"+End.Year+"\n");

                idPeriodTxtVw.setText("CATEGORY "+selectedCategory+"\n");
                idPeriodTxtVw.append(Start.Month+"/"+Start.Year+" <-> "+End.Month+"/"+End.Year+"\n");
                idPeriodTxtVw.append("Processing....");
                // Analyze data with Category
                clearBuffers(getApplicationContext());
            }
        });
    }


    private void variableInit() {
        idPeriodBtn   = findViewById(R.id.idPeriodBtn);

        /////////////////////////  Spinner  ///////////////////////////
        idCategoryList = (Spinner) findViewById(R.id.idCategoryList);
        idCategoryList.setOnItemSelectedListener(this);
        idCategoryList.setVisibility(View.VISIBLE);

        idCategoryAryLst.add(getString(R.string.CATEGORY1));
        idCategoryAryLst.add(getString(R.string.CATEGORY2));
        idCategoryAryLst.add(getString(R.string.CATEGORY3));
        idCategoryAryLst.add(getString(R.string.CATEGORY4));
        idCategoryAryLst.add(getString(R.string.CATEGORY5));
        idCategoryAryLst.add(getString(R.string.CATEGORY6));

        idCategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idCategoryAryLst);
        idCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idCategoryList.setAdapter(idCategoryAdapter);


        /////////////////////////  Number Picker Start Calendar  ///////////////////////////
        idPeriodSrtMnth = findViewById(R.id.idPeriodSrtMnth);
        idPeriodSrtYr   = findViewById(R.id.idPeriodSrtYr);

        idPeriodSrtYr.setMinValue(getResources().getInteger(R.integer.START_YEAR));
        idPeriodSrtYr.setMaxValue(getResources().getInteger(R.integer.END_YEAR));
        idPeriodSrtYr.setWrapSelectorWheel(true);

        idPeriodSrtMnth.setMinValue(getResources().getInteger(R.integer.START_MONTH));
        idPeriodSrtMnth.setMaxValue(getResources().getInteger(R.integer.END_MONTH));
        idPeriodSrtMnth.setWrapSelectorWheel(true);
        idPeriodSrtMnth.setDisplayedValues(MnthValues);

        /////////////////////////  Number Picker End Calendar  ///////////////////////////
        idPeriodEndMnth = findViewById(R.id.idPeriodEndMnth);
        idPeriodEndYr   = findViewById(R.id.idPeriodEndYr);

        idPeriodEndYr.setMinValue(getResources().getInteger(R.integer.START_YEAR));
        idPeriodEndYr.setMaxValue(getResources().getInteger(R.integer.END_YEAR));
        idPeriodEndYr.setWrapSelectorWheel(true);

        idPeriodEndMnth.setMinValue(getResources().getInteger(R.integer.START_MONTH));
        idPeriodEndMnth.setMaxValue(getResources().getInteger(R.integer.END_MONTH));
        idPeriodEndMnth.setWrapSelectorWheel(true);
        idPeriodEndMnth.setDisplayedValues(MnthValues);

        /////////////////////////  TextView  ///////////////////////////
        idPeriodTxtVw = findViewById(R.id.idPeriodTxtVw);
        idPeriodTxtVw.setText("Testing");
    }

    private void clearBuffers(Context context ) {
        resetPickerValues(idPeriodSrtMnth, idPeriodSrtYr);
        resetPickerValues(idPeriodEndMnth, idPeriodEndYr);
        idCategoryList.setSelection(0);
        selectedCategory = getString(R.string.CATEGORY1);
    }

    private CurrentDate readPickerValues(NumberPicker idMnth, NumberPicker idYr) {
        CurrentDate date;
        date = new CurrentDate(0, idMnth.getValue(), idYr.getValue());
        return date;
    }

    private void resetPickerValues(NumberPicker idMnth, NumberPicker idYr) {
        idMnth.setValue(idMnth.getMinValue());
        idYr.setValue(idYr.getMinValue());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = adapterView.getItemAtPosition(i).toString();
        Log.e(getString(R.string.LOG_PERIODIC_STATS), "onItemSelected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e(getString(R.string.LOG_PERIODIC_STATS), "onNothingSelected");
    }
}