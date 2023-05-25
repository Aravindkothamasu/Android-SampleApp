package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class CurrentDate {
    int DayOfMonth;
    int Month;
    int Year;
    CurrentDate( int dd, int mm, int yr)
    {
        DayOfMonth = dd;
        Month      = mm;
        Year       = yr;
    }
}

public class DatabaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Variable Declaration.
    private EditText AmountEdt, ItemPurchaseEdt;
    private Button addPurchaseBtn;
    private DBHandler dbHandler;
    private CalendarView calendarDate;
    String selectedCategory;
    CurrentDate selectedDate;
    ArrayList<String> expseSpnerAryLst = new ArrayList<String>();
    ArrayAdapter<String> expseAdapter;
    Spinner expseSpner;
    boolean isTesting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;

        setContentView(R.layout.activity_database);

        intent = getIntent();
        isTesting = intent.getBooleanExtra("isTesting", false);



        Log.e( getString(R.string.DB), "OnCreate isTesting : "+isTesting);

        variableInit();

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(DatabaseActivity.this, isTesting ? getString(R.string.DB_FILENAME_TESTING) : getString(R.string.DB_FILENAME_RELEASE));
        Log.e( getString(R.string.DB), "After new DBHandler Called");

        calendarDate.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
                Log.e(getString(R.string.DB), dayOfMonth + "/" + (month+1) + "/" + year);
                selectedDate.Year  = year;
                selectedDate.Month = month+1;
                selectedDate.DayOfMonth = dayOfMonth;
                // Log.e(getString(R.string.DB), "ECHCK IT CARE " + selectedDate.DayOfMonth + "/" + selectedDate.Month + "/" + selectedDate.Year);
            }
        });

        // below line is to add on click listener for our add course button.
        addPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String enteredItem = ItemPurchaseEdt.getText().toString();
                int enteredAmt  = Integer.parseInt(AmountEdt.getText().toString());

                if( selectedDate.Year == 0 ) {
                    selectedDate = GetCurrDate(calendarDate);
                }
                // validating if the text fields are empty or not.
                if ( (enteredAmt != 0 && selectedDate.Year != 0) && (enteredItem.isEmpty() && selectedCategory == getString(R.string.CATEGORY_OTHER)) ) {
                    Log.e( getString(R.string.DB), "Please enter all the data");
                    Toast.makeText(DatabaseActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    clearBuffers(getApplicationContext(), v );
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                Log.e(getString(R.string.DB), selectedDate.DayOfMonth + "/" + selectedDate.Month + "/" + selectedDate.Year);

                // Calling Add new Course selector
                dbHandler.addNewCourse( selectedDate, enteredItem, enteredAmt, selectedCategory );

                Toast.makeText(DatabaseActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();

                // Clearing all the buffers from the list.
                clearBuffers(getApplicationContext(), v );
            }
        });
    }

    private void variableInit() {
        // initializing all our variables.
        selectedDate         = new CurrentDate(0, 0, 0);
        calendarDate         = findViewById(R.id.idCalendarDate);
        ItemPurchaseEdt      = findViewById(R.id.idItem);
        AmountEdt            = findViewById(R.id.idAmount);
        addPurchaseBtn       = findViewById(R.id.idBtnAddPurchase);

        // Spinner
        expseSpner           = (Spinner)findViewById(R.id.idSpinnerExpense);
        expseSpner.setOnItemSelectedListener(this);
        expseSpner.setVisibility(View.VISIBLE);

        expseSpnerAryLst.add(getString(R.string.CATEGORY1));
        expseSpnerAryLst.add(getString(R.string.CATEGORY2));
        expseSpnerAryLst.add(getString(R.string.CATEGORY3));
        expseSpnerAryLst.add(getString(R.string.CATEGORY4));
        expseSpnerAryLst.add(getString(R.string.CATEGORY5));
        expseSpnerAryLst.add(getString(R.string.CATEGORY6));

        expseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expseSpnerAryLst);
        expseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expseSpner.setAdapter(expseAdapter);
    }


    private void clearBuffers(Context context, View v ) {
        AmountEdt.setText("");
        ItemPurchaseEdt.setText("");
        calendarDate.setDate(calendarDate.getDate());
        selectedDate.Month = selectedDate.DayOfMonth = selectedDate.Year = 0;
        hideKeyboardFrom( context, v );
        expseSpner.setSelection(0);
        selectedCategory = getString(R.string.CATEGORY1);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    static CurrentDate GetCurrDate(CalendarView calendarDate ) {
        long selectedDate;
        DateFormat format;
        int dd, mm, yr;

        selectedDate = calendarDate.getDate();
        format = new SimpleDateFormat("dd");
        dd = Integer.parseInt(format.format(selectedDate));

        format = new SimpleDateFormat("MM");
        mm = Integer.parseInt(format.format(selectedDate));

        format = new SimpleDateFormat("YYYY");
        yr = Integer.parseInt(format.format(selectedDate));

        return new CurrentDate(dd, mm, yr);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = adapterView.getItemAtPosition(i).toString();
        Log.e( getString(R.string.DB), "onItemSelected Called "+ selectedCategory );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e( getString(R.string.DB), "onNothingSelected");
    }
}