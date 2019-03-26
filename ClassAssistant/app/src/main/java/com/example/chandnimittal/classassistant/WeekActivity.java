package com.example.chandnimittal.classassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chandnimittal.classassistant.Utils.LetterImageView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class WeekActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        setupUIView();
        initToolbar();
        setUpListView();
    }

    private void setupUIView() {
        toolbar = (Toolbar) findViewById(R.id.toolbarWeek);
        listView=(ListView)findViewById(R.id.lvWeek);
        sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Week");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpListView() {
        final String[] week = getResources().getStringArray(R.array.Week);

        WeekAdapter adapter = new WeekAdapter(this,R.layout.activiy_week_single_item,week);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0 :{
                        sharedPreferences.edit().putString(SEL_DAY, "MONDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 1 :{
                        sharedPreferences.edit().putString(SEL_DAY, "TUESDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 2 :{
                        sharedPreferences.edit().putString(SEL_DAY, "WEDNESDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 3 :{
                        sharedPreferences.edit().putString(SEL_DAY, "THURSDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 4 :{
                        sharedPreferences.edit().putString(SEL_DAY, "FRIDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 5 :{
                        sharedPreferences.edit().putString(SEL_DAY, "SATDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    case 6 :{
                        sharedPreferences.edit().putString(SEL_DAY, "SUNDAY").apply();
                        Intent modify_intent = new Intent(WeekActivity.this,TimeTable.class);
                        String selected_item = week[i];
                        modify_intent.putExtra("item", selected_item);
                        startActivity(modify_intent);
                        break;}
                    default :break;

                }
            }
        });
    }

    public class WeekAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater layoutInflater;
        private String[] week = new String[]{};

        public WeekAdapter( Context context, int resource,String[] objects) {
            super(context, resource,objects);
            this.resource = resource;
            this.week=objects;
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                holder = new ViewHolder();
                        convertView = layoutInflater.inflate(resource, null);
                        holder.ivLogo = (LetterImageView)convertView.findViewById(R.id.ivLetter);
                       holder.tvWeek= (TextView)convertView.findViewById(R.id.tvWeek);
                        convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.ivLogo.setOval(true);
            holder.ivLogo.setLetter(week[position].charAt(0));
            holder.tvWeek.setText(week[position]);
                return convertView;
        }
    }

    class ViewHolder{
        private LetterImageView ivLogo;
         private TextView tvWeek;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
