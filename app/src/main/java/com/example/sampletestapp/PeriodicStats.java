package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    boolean isTesting = false;
    Intent  intent;
    DBHandler dbHandler;
    private Button   idPeriodBtn;
    private TextView idPeriodTxtVw;
    private NumberPicker idPeriodSrtMnth, idPeriodSrtYr;
    private NumberPicker idPeriodEndMnth, idPeriodEndYr;
    private static final String MnthValues[] = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec" };

    // Below Spinner related variables
    private Spinner idCategoryList;
    String selectedCategory;
    ArrayList<String> CategoryNameList = new ArrayList<String>();
    ArrayAdapter<String> idCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_stats);
        Log.e(getString(R.string.LOG_PERIODIC_STATS), "Periodic Stats Called");

        dbHandler = new DBHandler(PeriodicStats.this, isTesting ? getString(R.string.DB_FILENAME_TESTING) : getString(R.string.DB_FILENAME_RELEASE));
        variableInit();
        resetPickerValues(idPeriodSrtMnth, idPeriodSrtYr);
        resetPickerValues(idPeriodEndMnth, idPeriodEndYr);


        idPeriodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentDate StartDate, EndDate;
                CategoryResult [] Rslt = null;

                Log.e(getString(R.string.LOG_PERIODIC_STATS), "PERIOD GET DATA CLICKED");
                StartDate = readPickerValues(idPeriodSrtMnth, idPeriodSrtYr);
                EndDate   = readPickerValues(idPeriodEndMnth, idPeriodEndYr);

                Log.e( getString(R.string.LOG_PERIODIC_STATS), "CATEGORY "+selectedCategory+"\n");
                Log.e( getString(R.string.LOG_PERIODIC_STATS), "START MM/YY : "+ StartDate.Month+"/"+StartDate.Year+"\n");
                Log.e( getString(R.string.LOG_PERIODIC_STATS), "START MM/YY : "+ EndDate.Month+"/"+EndDate.Year+"\n");

                idPeriodTxtVw.setText("CATEGORY "+selectedCategory+"\n");
                idPeriodTxtVw.append( StartDate.Month+"/"+StartDate.Year+" <-> "+EndDate.Month+"/"+EndDate.Year+"\n");
                idPeriodTxtVw.append("Processing....");


                if( true == validateInputDates( StartDate, EndDate )) {
                    Log.e( getString(R.string.LOG_PERIODIC_STATS), "INPUT CALENDER DATES VERIFIED");

                    Rslt = dbHandler.getPeriodicStats(StartDate, EndDate, selectedCategory);

                    idPeriodTxtVw.setText("CAT "+selectedCategory+ " "+StartDate.Month+"/"+StartDate.Year+" <-> "+EndDate.Month+"/"+EndDate.Year+"\n");
                    for( int iterator = 0; iterator < Rslt.length; iterator++ ) {
                        if( Rslt[iterator].isValid == true ) {
                            idPeriodTxtVw.append(Rslt[iterator].Month+"/"+Rslt[iterator].Year +" Amt "+Rslt[iterator].CategoryValue+"\n");
                        }
                    }
                } else {
                    Log.e(getString(R.string.LOG_PERIODIC_STATS), "INPUT CALENDER DATES WRONG");
                }
                clearBuffers(getApplicationContext());
            }
        });
    }

    private boolean validateInputDates( CurrentDate StartDate, CurrentDate EndDate ) {
        boolean Rtn = false;

        if( StartDate.Year > EndDate.Year ) {                   // Start Date should Year should be less than EndDate.
            Rtn = false;
        } else if ( StartDate.Year < EndDate.Year ) {
            Rtn = true;
        } else if ( StartDate.Year == EndDate.Year ) {
            if( StartDate.Month < EndDate.Month) {
                Rtn = true;
            } else {
                Rtn = false;
            }
        }
        return Rtn;
    }


    private void variableInit() {
        intent = getIntent();
        isTesting = intent.getBooleanExtra("isTesting", false);

        idPeriodBtn   = findViewById(R.id.idPeriodBtn);

        /////////////////////////  Spinner  ///////////////////////////
        idCategoryList = (Spinner) findViewById(R.id.idCategoryList);
        idCategoryList.setOnItemSelectedListener(this);
        idCategoryList.setVisibility(View.VISIBLE);

        dbHandler.feedCategoryNameList(CategoryNameList, getApplicationContext());

        idCategoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CategoryNameList);
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