package com.example.frank.coursescheduleforbentleystudent;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CourseDetail extends AppCompatActivity {

    private TextView course;
    private TextView loc;
    private TextView time;
    private TextView assignment;
    private TextView instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        SQLiteDatabase db = LogInActivity.db;
        String Courseid=StudentMenuActivity.course;
        course=findViewById(R.id.classdetail);
        loc=findViewById(R.id.location);
        time=findViewById(R.id.texttime);
        assignment=findViewById(R.id.assignmentinfo);
        instructor=findViewById(R.id.instructorinfo);
        course.setText(Courseid);
    }
}
