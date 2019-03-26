package com.example.chandnimittal.classassistant;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
    TodoDbHelper dbhelper;
    ArrayAdapter<String> maAapter;
    ListView listView;
    private Toolbar toolbar;

    //load all tasks
    private void loadAllTasks(){
        ArrayList<String> taskList = dbhelper.getTaskList();

        if(maAapter == null) {
            maAapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            listView.setAdapter(maAapter);
        } else {
            maAapter.clear();
            maAapter.addAll(taskList);
            maAapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todomenu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        toolbar = (Toolbar)findViewById(R.id.toolbarTodo);
        listView = (ListView)findViewById(R.id.todoListView);
        initToolbar();

        dbhelper = new TodoDbHelper(this);
        loadAllTasks();
    }

    public void deleteTask(View view) {
        try{
            int index = listView.getPositionForView(view);
            String task = maAapter.getItem(index++);
            dbhelper.deleteTask(task);
            loadAllTasks();
        }
        catch(Exception e) {
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTodo:
                final EditText editText = new EditText(this);
                Log.i("BUTTON CLICKED",editText.getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("ADD NEW TASK")
                        .setMessage("Whats your task")
                        .setView(editText)
                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task = String.valueOf(editText.getText());
                                dbhelper.insertNewTask(task);
                                //todo
                                //loadtasks
                                loadAllTasks();
                            }
                        })
                        .setNegativeButton("CANCEL",null)
                        .create();
                dialog.show();
                return true;
            case android.R.id.home : {
                onBackPressed();
            }

                }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("To-Do");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
