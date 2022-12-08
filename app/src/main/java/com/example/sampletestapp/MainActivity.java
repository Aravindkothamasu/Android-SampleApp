package com.example.sampletestapp;

import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;
import com.example.sampletestapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AppBarConfiguration appBarConfiguration;
    // private ActivityMainBinding binding;
    private TextView textview1;
    private EditText edittext1;
    Button B1, B2, B3;
    CheckBox C1;
    ToggleButton T1;
    RadioButton R1;

    RadioGroup RG1;
    RadioButton RGb1;

    private ProgressDialog progress;

    Spinner spinner1;

    broadcast airplaneModeChangeReceiver = new broadcast();


    // TimePicker timepicker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setSupportActionBar(binding.toolbar);
        setContentView(R.layout.activity_main);


        // Button Defining
        B1 = (Button) findViewById(R.id.Button1);
        B2 = (Button) findViewById(R.id.Button2);
        B3 = (Button) findViewById(R.id.Button3);

        // Text View Defining
        textview1 = (TextView) findViewById(R.id.SampleText);

        // Edit Text Box Defining
        edittext1 = (EditText)findViewById(R.id.editText1);

        // CheckBox Defining
        C1= (CheckBox)findViewById(R.id.CheckBox1);

        // Toggle Button
        T1 = (ToggleButton)findViewById(R.id.ToggleButton1);

        // Radio Button
        R1 = (RadioButton)findViewById(R.id.RadioButton1);

        // Radio Group
        RG1 = (RadioGroup)findViewById(R.id.RadioGroup1);


        // Spinner
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner1.setVisibility(View.VISIBLE);


        ArrayList<String> country=new ArrayList<String>();
        country.add("India");
        country.add("US");
        country.add("Russia");
        country.add("Germany");
        country.add("Ukraine");
        country.add("China");
        country.add("Italy");
        country.add("Canada");
        country.add("Nepal");
        country.add("Sri Lanka");
        country.add("Bangkok");



        ArrayAdapter<String> CountryAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country);
        spinner1.setAdapter(CountryAdapter);

        // timepicker1 = (TimePicker)findViewById(R.id.timepicker1);

        Toast.makeText(this, "INSIDE ONCREAT", Toast.LENGTH_SHORT).show();
        textview1.setText("onCreate Called");

        // BUTTON 1
        B1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean c, r, t;
                int Rg=0;

                t=T1.isChecked();
                c=C1.isChecked();
                r=R1.isChecked();
                Rg=RG1.getCheckedRadioButtonId();

                B1.setText( "SQUEEZE " + getString(R.string.BUT_1_NAME) );
                if( Rg != -1 )
                {
                    RGb1 = (RadioButton) findViewById(Rg);
                    Toast.makeText(MainActivity.this, "BUT 1 : " + edittext1.getText().toString() + " " + RGb1.getText(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "BUT 1 : " + edittext1.getText().toString() + " NO SWITCH CLICKED", Toast.LENGTH_SHORT).show();
                }

                // Toast.makeText(MainActivity.this, "BUT 1 "+edittext1.getText().toString()+" "+c+" "+t+" "+r, Toast.LENGTH_SHORT).show();
                // textview1.setText("BUT1 "+edittext1.getText().toString()+" "+c+" "+t+" "+r)
            }
        });

        // BUTTON 2
        B2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean c, r, t;
                t=T1.isChecked();
                c=C1.isChecked();
                r=R1.isChecked();

                B2.setText( "SQUEEZE " + getString(R.string.BUT_2_NAME) );
                Toast.makeText(MainActivity.this, "BUT 2 "+edittext1.getText().toString()+" "+c+" "+t+" "+r, Toast.LENGTH_SHORT).show();
                textview1.setText("BUT2 "+edittext1.getText().toString()+" "+c+" "+t+" "+r);

                /*
                progress=new ProgressDialog(MainActivity.this);
                progress.setTitle("ARAVIND KOTHAMASU"); // Setting Title
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(false);
                progress.setProgress(0);
                progress.show();
                progress.setCancelable(false);


                final int totalProgress = 100;
                final Thread t = new Thread()
                {
                    @Override
                    public void run() {
                        int jumpTime = 0;

                        while (jumpTime < totalProgress)
                        {
                            try
                            {
                                sleep(500);
                                jumpTime += 5;
                                progress.setProgress(jumpTime);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        progress.dismiss();
                    }
                };
                t.start();
                */
            }
        });

        B2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Toast.makeText(MainActivity.this, "BUT 3 ON KEY LISTENER", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // BUTTON 3
        B3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean c, r, t;
                t=T1.isChecked();
                c=C1.isChecked();
                r=R1.isChecked();

                B3.setText( "SQUEEZE "+ getString(R.string.BUT_3_NAME)  );
                Toast.makeText(MainActivity.this, "BUT 3 "+edittext1.getText().toString()+" "+c+" "+t+" "+r, Toast.LENGTH_SHORT).show();
                textview1.setText("BUT3 "+edittext1.getText().toString()+" "+c+" "+t+" "+r);
            }

            public boolean onLongClick(View V)
            {
                Toast.makeText(MainActivity.this, "BUT 3 ON LONG CLICK", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        B3.setOnLongClickListener( new View.OnLongClickListener()  {
            @Override
            public boolean onLongClick(View V)
            {
                Toast.makeText(MainActivity.this, "BUT 3 ON LONG CLICK", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        B3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                Toast.makeText(MainActivity.this, "BUT 3 ON FOCUS CHANGE", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "INSIDE ONSTART", Toast.LENGTH_SHORT).show();
        textview1.setText("onStart Called");
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "INSIDE ONPAUSE", Toast.LENGTH_SHORT).show();
        textview1.setText("onPause Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "INSIDE ONDESTROY", Toast.LENGTH_SHORT).show();
        textview1.setText("onDestroy Called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "INSIDE ONTOUCH", Toast.LENGTH_SHORT).show();
        textview1.setText("onTouch Called");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Spinner autogenerated Functions - 1
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        String tv_text = textview1.getText().toString();
        Toast.makeText ( getApplicationContext(),tv_text+" "+item, Toast.LENGTH_SHORT).show();
    }

    // Spinner autogenerated Functions - 2
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText ( getApplicationContext(), "SPINNER NOTHING CALLED", Toast.LENGTH_SHORT).show();
    }
}