package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
public class DatabaseActivity extends AppCompatActivity {
    private EditText courseNameEdt;
    private Button addCourseBtn;
    private DBHandler dbHandler;
    private CalendarView calendarDate;
    CurrentDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Log.e( getString(R.string.DB), "OnCreate Called");
        // initializing all our variables.
        selectedDate         = new CurrentDate(0, 0, 0);
        courseNameEdt        = findViewById(R.id.idEdtCoureName);
        addCourseBtn         = findViewById(R.id.idBtnAddCourse);
        calendarDate         = findViewById(R.id.idCalendarDate);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(DatabaseActivity.this);
        Log.e( getString(R.string.DB), "After new DBHandler Called");

        calendarDate.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + 1 + "/" + year, Toast.LENGTH_LONG).show();
                Log.e(getString(R.string.DB), dayOfMonth + "/" + month + 1 + "/" + year);
                selectedDate.Year  = year;
                selectedDate.Month = month+1;
                selectedDate.DayOfMonth = dayOfMonth;
            }
        });
        // below line is to add on click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String courseName = courseNameEdt.getText().toString();


                if( selectedDate.Year == 0 ) {
                    selectedDate = GetCurrDate(calendarDate);
                }
                // validating if the text fields are empty or not.
                if (courseName.isEmpty() && selectedDate.Year != 0) {
                    Toast.makeText(DatabaseActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                Log.e(getString(R.string.DB), selectedDate.DayOfMonth + "/" + selectedDate.Month + "/" + selectedDate.Year);

                dbHandler.addNewCourse(courseName, selectedDate);
                // after adding the data we are displaying a toast message.
                Toast.makeText(DatabaseActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                courseNameEdt.setText("");
                calendarDate.setDate(calendarDate.getDate());
                selectedDate.Month = selectedDate.DayOfMonth = selectedDate.Year = 0;
            }
        });
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
}