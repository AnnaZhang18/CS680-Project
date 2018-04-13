package com.example.frank.coursescheduleforbentleystudent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StudentMenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView CourseNum;
    private ListView CourseLoc;
    private ListView CourseTime;
    ArrayList<String> CNcontent = new ArrayList<>();
    ArrayList<String> CLcontent = new ArrayList<>();
    ArrayList<String> CTcontent = new ArrayList<>();
    private Cursor cursor;
    ArrayAdapter<String> CNaa;
    ArrayAdapter<String> CLaa;
    ArrayAdapter<String> CTaa;
    public static String course;
    private String account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        TextView Today = findViewById(R.id.todayclass);

        Calendar sCalendar = Calendar.getInstance();
        String dayName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");
        String date = df.format(Calendar.getInstance().getTime());
        Today.setText(Today.getText().toString()+"            "+date+"     "+dayName);
        dayName = dayName.trim().substring(0,1);


        CourseNum = findViewById(R.id.todaylistcourse);
        CourseLoc = findViewById(R.id.todaylistlocation);
        CourseTime = findViewById(R.id.todaylisttime);

        account = LogInActivity.account;
        SQLiteDatabase db = LogInActivity.db;
//        Toast.makeText(getApplicationContext(), LogInActivity.account, Toast.LENGTH_LONG).show();
        cursor = db.rawQuery("SELECT CoursesSection FROM Schedule WHERE Email="+"\""+account+"\"",null);
//        Toast.makeText(getApplicationContext(), "a", Toast.LENGTH_LONG).show();
        cursor.moveToFirst();
        do{
            CNcontent.add(cursor.getString(0));
        }while(cursor.moveToNext());

        for(int i =0; i<CNcontent.size();i++) {
            cursor = db.rawQuery("SELECT Day, Time,LocationID FROM CoursesDetail WHERE CoursesSection=" + "\"" + CNcontent.get(i) + "\"", null);
            cursor.moveToFirst();
            String day = cursor.getString(0);
            if(day.trim().contains(dayName)) {
//                cursor = db.rawQuery("SELECT Day FROM CoursesDetail WHERE CoursesSection=" + "\"" + CNcontent.get(i) + "\"", null);
//                cursor.moveToFirst();
                CTcontent.add(cursor.getString(1));
                CLcontent.add(cursor.getString(2));
            }
            else{
                CNcontent.remove(i);
                i--;
            }
        }


        CNaa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,     //Android supplied List item format
                CNcontent);
        CourseNum.setAdapter(CNaa);

        CLaa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,     //Android supplied List item format
                CLcontent);
        CourseLoc.setAdapter(CLaa);

        CTaa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,     //Android supplied List item format
                CTcontent);
        CourseTime.setAdapter(CTaa);

        CourseNum.setOnItemClickListener(this);
        CourseLoc.setOnItemClickListener(this);
        CourseTime.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        course=CNcontent.get(position);
        openCourseDetail();
    }

    public void openCourseDetail(){
        Intent i = new Intent(this, CourseDetail.class);
        startActivity(i);
    }
}
