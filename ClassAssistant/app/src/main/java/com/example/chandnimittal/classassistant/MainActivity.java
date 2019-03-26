package com.example.chandnimittal.classassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarAttendance);
        listView = findViewById(R.id.lvMain);



        initToolbar();
        setupListView();
    }



    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Timetable App");
    }

    private void setupListView() {

        String[] title = getResources().getStringArray(R.array.Main);
        String[] description = getResources().getStringArray(R.array.Description);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, title, description);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                       Intent intent = new Intent(MainActivity.this, WeekActivity.class);
                       startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                        startActivity(intent);
                        break;

                    }
                    case 3: {
                        FirebaseAuth firebaseAuth;
                        firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(MainActivity.this, IntroActivity.class));
                            break;

                    }

                }
            }
        });
    }

    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView title, description;
        private String[] titleArray;
        private String[] descriptionArray;
        private ImageView imageView;

        public SimpleAdapter(Context context, String[] title, String[] description) {
            mContext = context;
            titleArray = title;
            descriptionArray = description;
            layoutInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return titleArray.length;
        }

        @Override
        public Object getItem(int position) {
            return titleArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.main_activity_single_file, null);
            }

            title = convertView.findViewById(R.id.tvMain);
            description = convertView.findViewById(R.id.tvDescription);
            imageView = convertView.findViewById(R.id.ivMain);

            title.setText(titleArray[position]);
            description.setText(descriptionArray[position]);

            if (titleArray[position].equalsIgnoreCase("Timetable")) {
                imageView.setImageResource(R.drawable.book_new);
            } else if (titleArray[position].equalsIgnoreCase("Subjects")) {
                imageView.setImageResource(R.drawable.subjects);
            } else if (titleArray[position].equalsIgnoreCase("To-Do")) {
                imageView.setImageResource(R.drawable.todo_new);}
                    else if (titleArray[position].equalsIgnoreCase("Logout")) {
                imageView.setImageResource(R.drawable.logout);
            }
            else {
                imageView.setImageResource(R.drawable.attendance);
            }

            return convertView;

        }
    }

}




