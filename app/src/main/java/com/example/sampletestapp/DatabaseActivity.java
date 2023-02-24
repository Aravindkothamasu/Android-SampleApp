package com.example.sampletestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;


public class DatabaseActivity extends AppCompatActivity {
    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
    private Button addCourseBtn;
    private DBHandler dbHandler;
    private CalendarView calendarDate;
    private int Year, Month, DayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Log.e( getString(R.string.DB), "OnCreate Called");

        // initializing all our variables.
        courseNameEdt        = findViewById(R.id.idEdtCoureName);
        courseTracksEdt      = findViewById(R.id.idEdtCourseTracks);
        courseDurationEdt    = findViewById(R.id.idEdtCourseDuration);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
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
                Year  = year;
                Month = month+1;
                DayOfMonth = dayOfMonth;
            }
        });
        // below line is to add on click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String courseName = courseNameEdt.getText().toString();
                String courseTracks = courseTracksEdt.getText().toString();
                String courseDuration = courseDurationEdt.getText().toString();
                String courseDescription = courseDescriptionEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (courseName.isEmpty() && courseTracks.isEmpty() && courseDuration.isEmpty() && courseDescription.isEmpty()) {
                    Toast.makeText(DatabaseActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                Log.e(getString(R.string.DB), DayOfMonth + "/" + Month + "/" + Year);
                dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks, 20);
                // after adding the data we are displaying a toast message.
                Toast.makeText(DatabaseActivity.this, "Course has been added.", Toast.LENGTH_SHORT).show();
                courseNameEdt.setText("");
                courseDurationEdt.setText("");
                courseTracksEdt.setText("");
                courseDescriptionEdt.setText("");
                DayOfMonth = Month = Year = 0;
                calendarDate.setDate(calendarDate.getDate());
            }
        });
    }
}