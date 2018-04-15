package com.example.frank.coursescheduleforbentleystudent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Cursor cursor = db.rawQuery("SELECT Day,Time,LocationID,CourseID FROM CoursesDetail WHERE CoursesSection=" + "\"" + Courseid + "\"", null);
        cursor.moveToFirst();
        time.setText(time.getText().toString()+cursor.getString(0)+" "+cursor.getString(1));
        loc.setText(loc.getText().toString()+cursor.getString(2));
        cursor = db.rawQuery("SELECT Coursename,TeacherID FROM Courses WHERE CourseID=" + "\"" + cursor.getString(3) + "\"", null);
        cursor.moveToFirst();
        course.setText(Courseid+"     "+cursor.getString(0));
        cursor = db.rawQuery("SELECT Name,TeacherEmail FROM Teacher WHERE TeacherID=" + "\"" + cursor.getString(1) + "\"", null);
        cursor.moveToFirst();
        instructor.setText(cursor.getString(0)+"\n"+cursor.getString(1));

    }
}
