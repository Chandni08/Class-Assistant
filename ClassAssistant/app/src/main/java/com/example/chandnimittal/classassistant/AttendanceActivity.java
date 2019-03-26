package com.example.chandnimittal.classassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.TextView;

public class AttendanceActivity extends AppCompatActivity {
    private Toolbar toolbaratt;
    private CheckBox presentCB;
    private CheckBox absentCB;
    private CheckBox cancelCB;
    private TextView TVatt;
    int present_count=0;
    int absent_count=0;
    int cancel_count=0;
   // public SubAtt subAtt = new SubAtt();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        toolbaratt =(Toolbar)findViewById(R.id.toolbarAtt);
        presentCB = (CheckBox) findViewById(R.id.presentcheckBox);
        absentCB = (CheckBox) findViewById(R.id.absentcheckBox);
        cancelCB = (CheckBox) findViewById(R.id.cancelcheckBox);
        TVatt = (TextView) findViewById(R.id.disatt);
       // String sname= getIntent().getStringExtra("SUBJECT NAME");
      //  subAtt.subname = sname;
        initToolbar();

        if(presentCB.isChecked()){
         present_count++;

            float disat =  calcAttendance(present_count,absent_count,cancel_count);
            String dis = Float.toString(disat);
            TVatt.setText(dis);
        }
        if(absentCB.isChecked()){
            absent_count++;

            float disat =  calcAttendance(present_count,absent_count,cancel_count);
            String dis = Float.toString(disat);
            TVatt.setText(dis);
        }if(cancelCB.isChecked()){
            cancel_count++;

            float disat =  calcAttendance(present_count,absent_count,cancel_count);
            String dis = Float.toString(disat);
            TVatt.setText(dis);
        }



    }

    private void initToolbar() {
        setSupportActionBar(toolbaratt);
        getSupportActionBar().setTitle("Mark Attendance");
    }

    private float calcAttendance(int p,int a,int c){
        float att_count = (float)(p* 100)/(p+a+c);
        return att_count;
    }
}
