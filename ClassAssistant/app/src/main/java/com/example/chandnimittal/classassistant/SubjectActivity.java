package com.example.chandnimittal.classassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;



public class SubjectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
   //DBHelper db;
    Cursor c;
  // RecordCursorAdapter recordCursorAdapter;
   // private ArrayAdapter<String> mySubAdapter;
    SubjectDbHelper subjectDbHelper;
    public ArrayList<String> subjectNameList = new ArrayList<String>() ;
   public ArrayList<String> subjectCodeList;
   public ArrayList<String> subjectCreditList;
 //  public ArrayList<SubAtt> subAtts = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        setupUIView();
        initToolbar();
        subjectDbHelper = new SubjectDbHelper(this);
        loadAllTasks();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
              //  String name = (String) adapter.getItemAtPosition(position);
                Intent appInfo = new Intent(SubjectActivity.this, AttendanceActivity.class);
              //  appInfo.putExtra("SUBJECT NAME", name);
                startActivity(appInfo);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView arg0, View v, int position, long id) {
                AlertDialog.Builder ad = new AlertDialog.Builder(SubjectActivity.this);
                ad.setTitle("Delete");
                ad.setMessage("Sure you want to delete record ?");
                final int pos = position;
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete of record from Database and List view.
                       c.moveToPosition(pos);
                       subjectDbHelper.deleteSubject(c.getInt(c.getColumnIndex(SubjectDbHelper.KEY_ID)));
                       // c=subjectDbHelper.getAll();
                        //subjectDbHelper.deleteSubject(which);
                        //c.requery();
                        notifyAll();
                      //  loadAllTasks();
                    }
                });
                ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
                return false;
            }
        });
    }

    private void setupUIView() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSubject);
        listView = (ListView) findViewById(R.id.lvSubject);
        //sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void loadAllTasks(){
        ArrayList<HashMap<String, String>> userList = subjectDbHelper.GetUsers();
        ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.subject_detail_single_item,new String[]{"name","credits","code"}, new int[]{R.id.tvSubName, R.id.tvSubCredit, R.id.tvSubCode});
        listView.setAdapter(adapter);
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTask:
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText codeText = new EditText(this);
                Log.i("BUTTON CLICKED",codeText.getText().toString());
                codeText.setHint("Subject Code");
                layout.addView(codeText);

                final EditText nameText = new EditText(this);
                nameText.setHint("Subject Name");
                layout.addView(nameText);

                final EditText creditText = new EditText(this);
                creditText.setHint("Subject Credits");
                layout.addView(creditText);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("ADD NEW Subject")
                        .setView(layout)
                        .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String subjectcode = String.valueOf(codeText.getText());
                                String subjectname = String.valueOf(nameText.getText());
                                String subjectcredit = String.valueOf(creditText.getText());
                                subjectNameList.add(subjectname);
                                subjectDbHelper.insertSubjectDetails(subjectname,subjectcode,subjectcredit);
                               // subjectList.add(subjectname);
                               // Log.i("SUBJECT",subjectList.get(0));
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



}
