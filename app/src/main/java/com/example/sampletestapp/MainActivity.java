package com.example.sampletestapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;
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
    RadioButton R1;

    RadioGroup RG1;
    RadioButton RGb1;

    // Progess Bar for Button 2 is Clicked
    private ProgressDialog progress;

    broadcast airplaneModeChangeReceiver = new broadcast();
    ServiceManager serv = new ServiceManager();
    String Str = null;

    // Floating Action Button Decleration
    FloatingActionButton idAddExpenseBtn, idExpenseStatBtn;
    ExtendedFloatingActionButton idExpenseBtn;
    TextView idAddExpenseText, idExpenseStatBtnText;
    Boolean isAllFABVisible = false;

    // Floating Action Button Animation Decleartion.
    Animation rotateOpen, rotateClose;
    Animation fromBottom, toBottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setSupportActionBar(binding.toolbar);
        setContentView(R.layout.activity_main);
        Log.e( getString(R.string.TAG1), "In OnCreate Called");
        // Button Defining

        InitVariables();

        ////////////////////////////////////////////////////////////////
        Log.e( getString(R.string.TAG1), "Initalize Variables Done");
        idExpenseBtn.shrink();

        idExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( getString(R.string.TAG1), "Add Action Fab Set on Click Listener "+isAllFABVisible);
                Log.e( getString(R.string.TAG1), "isALL FAB AVALIABLE "+isAllFABVisible);

                if (!isAllFABVisible) {
                    ExpandFloatingActionButton();
                } else {
                    ShrinkFloatingActionButton();
                }
            }
        });

        idExpenseStatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( getString(R.string.TAG1), "Purchase Stats Click Called");
                Toast.makeText(MainActivity.this, "Purchase Stats", Toast.LENGTH_SHORT).show();
                CheckForFABExpand();

                Intent intent = new Intent(MainActivity.this, PurchaseStats.class);
                intent.putExtra("isTesting", C1.isChecked());
                startActivity(intent);
            }
        });

        idAddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( getString(R.string.TAG1), "Add Purchase Click Called");
                Toast.makeText(MainActivity.this, "Add Purchase", Toast.LENGTH_SHORT).show();
                CheckForFABExpand();

                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                intent.putExtra("isTesting", C1.isChecked());
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////////////


        // timepicker1 = (TimePicker)findViewById(R.id.timepicker1);

        Log.e( getString(R.string.TAG1), "In Oncreate Called");
        Str = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e( getString(R.string.TAG1), "After String of Enviornment");



        textview1.setText("onCreate Called");

        // BUTTON 1
        B1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean r;
                int Rg=0;

                CheckForFABExpand();

                r=R1.isChecked();
                Rg=RG1.getCheckedRadioButtonId();

                B1.setText( "SQUEEZE " + getString(R.string.BUT_1_NAME));
                if( Rg != -1 )
                {
                    RGb1 = (RadioButton) findViewById(Rg);
                    Toast.makeText(MainActivity.this, "BUT 1 : " + edittext1.getText().toString() + " " + RGb1.getText(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "BUT 1 : " + edittext1.getText().toString() + " NO SWITCH CLICKED", Toast.LENGTH_SHORT).show();
                }

                Log.e( getString(R.string.TAG1), "Before Starting Second Activity in First Activity");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                Log.e( getString(R.string.TAG1), "After Starting Second Activity in First Activity");
                // Toast.makeText(MainActivity.this, "BUT 1 "+edittext1.getText().toString()+" "+c+" "+t+" "+r, Toast.LENGTH_SHORT).show();
                // textview1.setText("BUT1 "+edittext1.getText().toString()+" "+c+" "+t+" "+r)
            }
        });

        // BUTTON 2
        B2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean r;
                r=R1.isChecked();

                CheckForFABExpand();

                B2.setText( "SQUEEZE " + getString(R.string.BUT_2_NAME) );
                Toast.makeText(MainActivity.this, "BUT 2 "+edittext1.getText().toString()+" "+r, Toast.LENGTH_SHORT).show();
                textview1.setText("BUT2 "+edittext1.getText().toString()+" "+r);


                progress=new ProgressDialog(MainActivity.this);
                progress.setTitle("ARAVIND KOTHAMASU"); // Setting Title
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(false);
                progress.setProgress(0);
                progress.show();
                progress.setCancelable(false);


                final int totalProgress = 100;
                final Thread threadId = new Thread()
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
                threadId.start();

            }
        });

        B2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                CheckForFABExpand();

                Toast.makeText(MainActivity.this, "BUT 3 ON KEY LISTENER", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // BUTTON 3
        B3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View V)
            {
                Boolean r;
                r=R1.isChecked();

                CheckForFABExpand();

                B3.setText( "SQUEEZE " + getString(R.string.BUT_3_NAME) );
                Toast.makeText(MainActivity.this, "BUT 3 "+edittext1.getText().toString()+" "+r, Toast.LENGTH_SHORT).show();
                textview1.setText("BUT3 "+edittext1.getText().toString()+" "+r);


                if( serv.isNetworkAvaliable(MainActivity.this) == true ) {
                    textview1.setText( "SERV NET ABLE");
                    Log.e( getString(R.string.TAG1), "NET ABLE");
                } else {
                    textview1.setText("SERV NET NABLE");
                    Log.e( getString(R.string.TAG1), "NET NABLE");
                }
            }
        });


        B3.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View V)
            {
                CheckForFABExpand();
                B3.setText( "MORE SQUEEZE " + getString(R.string.BUT_3_NAME) );
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
        textview1.setText("onStart Called");
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        textview1.setText("onTouch Called");
        CheckForFABExpand();
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


    public void InitVariables() {
        B1 = (Button) findViewById(R.id.Button1);
        B2 = (Button) findViewById(R.id.Button2);
        B3 = (Button) findViewById(R.id.Button3);

        // Text View Defining
        textview1 = (TextView) findViewById(R.id.SampleText);

        // Edit Text Box Defining
        edittext1 = (EditText)findViewById(R.id.editText1);

        // CheckBox Defining
        C1= (CheckBox)findViewById(R.id.CheckBox1);

        // Radio Button
        R1 = (RadioButton)findViewById(R.id.RadioButton1);

        // Radio Group
        RG1 = (RadioGroup)findViewById(R.id.RadioGroup1);

        idAddExpenseBtn = findViewById(R.id.idAddExpense);
        idExpenseStatBtn = findViewById(R.id.idExpenseStat);
        idExpenseBtn = findViewById(R.id.idExpense);

        idAddExpenseText = findViewById(R.id.idAddExpenseText);
        idExpenseStatBtnText = findViewById(R.id.idExpenseStatText);

        idAddExpenseBtn.setVisibility(View.GONE);
        idExpenseStatBtn.setVisibility(View.GONE);
        idAddExpenseText.setVisibility(View.GONE);
        idExpenseStatBtnText.setVisibility(View.GONE);

        isAllFABVisible = false;

        rotateOpen = AnimationUtils.loadAnimation( MainActivity.this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation( MainActivity.this, R.anim.rotate_close_anim );
        fromBottom = AnimationUtils.loadAnimation( MainActivity.this, R.anim.from_bottom_anim );
        toBottom = AnimationUtils.loadAnimation( MainActivity.this, R.anim.to_bottom_anim );


    }

    // FAB Expanding and Shrink Functions

    public void CheckForFABExpand() {
        if( isAllFABVisible ) {
            ShrinkFloatingActionButton();
            Log.e( getString(R.string.TAG1), "CheckForFABExpand Called "+isAllFABVisible);
        }
    }

    public void ExpandFloatingActionButton() {
        /*
        // Methods used for without animation.
        idAddExpenseBtn.show();
        idExpenseStatBtn.show();
        // now extend the Ex FAB
        idExpenseBtn.extend();
         */

        idAddExpenseBtn.startAnimation(fromBottom);
        idExpenseStatBtn.startAnimation(fromBottom);
        idExpenseBtn.startAnimation(rotateOpen);

        idAddExpenseBtn.setVisibility(View.VISIBLE);
        idExpenseStatBtn.setVisibility(View.VISIBLE);
        idAddExpenseText.setVisibility(View.VISIBLE);
        idExpenseStatBtnText.setVisibility(View.VISIBLE);

        isAllFABVisible =true;
    }


    public void ShrinkFloatingActionButton() {
        /*
        // Methods used for without animation.
        idAddExpenseBtn.hide();
        idExpenseStatBtn.hide();
        idExpenseBtn.shrink();
         */
        idAddExpenseBtn.startAnimation(toBottom);
        idExpenseStatBtn.startAnimation(toBottom);
        idExpenseBtn.startAnimation(rotateClose);

        idAddExpenseBtn.setVisibility(View.INVISIBLE);
        idExpenseStatBtn.setVisibility(View.INVISIBLE);
        idAddExpenseText.setVisibility(View.GONE);
        idExpenseStatBtnText.setVisibility(View.GONE);

        isAllFABVisible = false;
    }
}
