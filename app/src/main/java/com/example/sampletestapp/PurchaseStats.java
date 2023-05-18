package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private DBHandler dbHandler;
    boolean isTesting = false;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_stats);
        Log.e( getString(R.string.STATS), "onCreate Called");

        variableInit();

        idStatGetData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentDate date;
                CategoryResult []Rslt = null;
                int TotMnthlyValue = 0;

                date  = readPickerValues();

                Log.e(getString(R.string.STATS), "TEST MN/YY : "+date.Month+"/"+date.Year);
                Toast.makeText(PurchaseStats.this, "STAT : MN/YY : "+date.Month+"/"+date.Year, Toast.LENGTH_SHORT).show();
                idTxtView.setText("Processing : "+date.Month+"/"+date.Year);

                resetPickerValues();

                dbHandler = new DBHandler(PurchaseStats.this,  isTesting ? getString(R.string.DB_FILENAME_TESTING) : getString(R.string.DB_FILENAME_RELEASE));
                Rslt = dbHandler.getMonthStats(date);
                if( Rslt != null ){
                    idTxtView.setText("");

                    for (int iterator =0; iterator < getResources().getInteger(R.integer.CATEGORY_COUNT); iterator++) {
                        Log.e(getString(R.string.STATS), "CAT :-> " + Rslt[iterator].CategoryName + " || AMOUNT :-> " + Rslt[iterator].CategoryValue );
                        TotMnthlyValue += Rslt[iterator].CategoryValue;
                        if( Rslt[iterator].CategoryValue != 0 ) {
                            idTxtView.append(Rslt[iterator].CategoryName + "  Rs. " + Rslt[iterator].CategoryValue + "\n");
                        }
                    }

                    Toast.makeText(PurchaseStats.this, "STAT : MN/YY : "+date.Month+"/"+date.Year+ " Rs."+TotMnthlyValue , Toast.LENGTH_SHORT).show();
                    idTxtView.append("MM/YY "+date.Month+"/"+date.Year+ " : Total Rs. "+TotMnthlyValue+"\n");
                } else {
                    Toast.makeText(PurchaseStats.this,"Table not found " + date.Month+"/"+date.Year,Toast.LENGTH_SHORT).show();
                    idTxtView.setText(date.Month+"/"+date.Year+" : Table not found");
                }
            }
        });
    }

    private CurrentDate readPickerValues() {
        CurrentDate date;
        date = new CurrentDate(0, idStatMnth.getValue(), idStatYr.getValue());
        return date;
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

        intent = getIntent();
        isTesting = intent.getBooleanExtra("isTesting", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e( getString(R.string.STATS), "onDestroy Called");
    }
}