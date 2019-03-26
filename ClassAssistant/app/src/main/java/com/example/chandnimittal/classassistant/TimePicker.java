package com.example.chandnimittal.classassistant;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TimePicker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView startTime;
    TextView endTime;
    Button save;
    Button cancel;
    Spinner subjectSpinner;
    String day_item;
    Spinner categorySpinner;
    TimeTableDbHelper timeTableDbHelper;
    String[] category = { "L", "T", "P", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        //this.setFinishOnTouchOutside(false);
        startTime = (TextView)findViewById(R.id.tv1);
        endTime = (TextView)findViewById(R.id.tv2);
        save = (Button)findViewById(R.id.savebutton);
        cancel = (Button)findViewById(R.id.cancelbutton);
        timeTableDbHelper = new TimeTableDbHelper(this);
        subjectSpinner = (Spinner)findViewById(R.id.subjectSpinner);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        subjectSpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            day_item = bundle.getString("item");
        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        categorySpinner.setAdapter(aa);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1100;
        params.width = 950;
        params.y = -10;

        this.getWindow().setAttributes(params);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startTime.getText().toString();
                String end = endTime.getText().toString();
                String subject = "";
                if(subjectSpinner.getSelectedItem() != null) {
                    subject = subjectSpinner.getSelectedItem().toString();
                }
                String category = categorySpinner.getSelectedItem().toString();
                timeTableDbHelper.insertDetails(day_item,start,end,subject,category);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onStartButtonClicked(View v){
        DialogFragment newFragment = new TimePickerFrag();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    public void onEndButtonClicked(View v){
        DialogFragment newFragment = new TimePickerFragt2();
        newFragment.show(getFragmentManager(),"TimePicker");
    }

    private void loadSpinnerData() {
        SubjectDbHelper db = new SubjectDbHelper(getApplicationContext());
      ArrayList<String> subjects = db.getAllSubjects();

        // Creating adapter for subjectSpinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subjects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to subjectSpinner
       subjectSpinner.setAdapter(dataAdapter);

       }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch(parent.getId()) {
            case R.id.subjectSpinner:
                // Do stuff for spinner1
                String label = parent.getItemAtPosition(i).toString();

                // Showing selected subjectSpinner item
                Toast.makeText(parent.getContext(), "You selected: " + label,
                        Toast.LENGTH_LONG).show();
                break;
             case R.id.categorySpinner:
                // Do stuff for spinner2
                 Toast.makeText(getApplicationContext(),category[i] , Toast.LENGTH_LONG).show();
                 break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
