package com.example.chandnimittal.classassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeTable extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    TimeTableDbHelper timeTableDbHelper;
    private SimpleCursorAdapter dataAdapter;
    String day_item;
    public static SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        timeTableDbHelper = new TimeTableDbHelper(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            day_item = bundle.getString("item");
        }
        setupUIView();
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllTasks();
    }

    private void setupUIView() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDayDetail);
        listView = (ListView) findViewById(R.id.lvDayDetail);
        sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TimeTable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadAllTasks(){
        ArrayList<HashMap<String, String>> userlist = timeTableDbHelper.GetUsers(day_item);
        Log.e("TEst", userlist.size() + " " + day_item);
        ListAdapter adapter = new SimpleAdapter(this,userlist,
                R.layout.timetable_single_item,
                new String[]{"starttime","endtime","subject","category"},
                new int[]{R.id.tvStartTime, R.id.tvEndTime,R.id.tvttSubName, R.id.tvCategory});
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTask:
                Intent modify_intent = new Intent(this, TimePicker.class);
                modify_intent.putExtra("item", day_item);
                startActivity(modify_intent);
                break;

            case android.R.id.home : {
                onBackPressed();
            }

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }
}