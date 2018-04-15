package com.example.frank.coursescheduleforbentleystudent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    private Button login;
    public static SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;
    private EditText email;
    private EditText password;
    public static String account;
    private int count;
    private String str;
    private OutputStreamWriter out;
    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login = findViewById(R.id.loginbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        try {
            FileInputStream in = openFileInput("account.txt");
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            String str = null;
            if(getFilesDir().exists()) {
                if ((str = reader.readLine()) != null) {
                    email.setText(str);
                    remember.setChecked(true);
                }
            }
            reader.close();

        }catch (IOException e) {}

//        Calendar sCalendar = Calendar.getInstance();
//        String dayName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//        Toast.makeText(getApplicationContext(), dayName, Toast.LENGTH_LONG).show();

        db = openOrCreateDatabase("CSBS.db",
                Context.MODE_PRIVATE, null);
        db.setLocale(Locale.getDefault());
        db.execSQL("Drop TABLE IF EXISTS 'Users'");
        db.execSQL("Create TABLE IF not EXISTS 'Users'('Email' Varchar(30) not null, 'Password' Varchar(30) not null)");
        db.execSQL("Drop TABLE IF EXISTS 'Teacher'");
        db.execSQL("Create TABLE IF not EXISTS 'Teacher'('TeacherID' Varchar(30) PRIMARY KEY not null, 'Name' Varchar(30) not null,'TeacherEmail' Varchar(30) not null,'Phone' Varchar(30) not null)");
        db.execSQL("Drop TABLE IF EXISTS 'Location'");
        db.execSQL("Create TABLE IF not EXISTS 'Location'('LocationID' Varchar(30) PRIMARY KEY not null, 'lat' Varchar(30) not null,'longt' Varchar(30) not null)");
        db.execSQL("Drop TABLE IF EXISTS 'Courses'");
        db.execSQL("Create TABLE IF not EXISTS 'Courses'('CourseID' Varchar(30) PRIMARY KEY not null,'TeacherID' Varchar(30)  not null,'Coursename' Varchar(30) not null,FOREIGN KEY('TeacherID') REFERENCES 'Teacher'('TeacherID'))");
        db.execSQL("Drop TABLE IF EXISTS 'CoursesDetail'");
        db.execSQL("Create TABLE IF not EXISTS 'CoursesDetail'('CoursesSection' Varchar(30) PRIMARY KEY not null,'CourseID' Varchar(30)  not null,'LocationID' Varchar(30) not null,'Day' Varchar(30) not null,'Time' Varchar(30) not null,FOREIGN KEY('CourseID') REFERENCES 'Courses'('CourseID'), FOREIGN KEY('LocationID') REFERENCES 'Location'('LocationID'))");
        db.execSQL("Drop TABLE IF EXISTS 'Schedule'");
        db.execSQL("Create TABLE IF not EXISTS 'Schedule'('CoursesSection' Varchar(30) not null,'Email' Varchar(30) not null, 'Note' Varchar(30) not null, PRIMARY KEY(CoursesSection,Email),FOREIGN KEY('CoursesSection') REFERENCES 'CoursesDetail'('CoursesSection'),FOREIGN KEY('Email') REFERENCES 'Users'('Email'))");

//        Toast.makeText(getApplicationContext(), "Table created", Toast.LENGTH_LONG).show();

//        db.execSQL("Create Table Users");
        db.execSQL("INSERT INTO Users Values('test@bentley.edu','12345')");
        db.execSQL("INSERT INTO Teacher Values('T01','Bill Schiano','SBill@bentley.edu','611-666-8888')");
        db.execSQL("INSERT INTO Courses Values('C01','T01','Information Technology')");
        db.execSQL("INSERT INTO CoursesDetail Values('CS603-100','C01','SMI102','MoWeFrSa','3:00PM-5:00PM')");
        db.execSQL("INSERT INTO Location Values('SMI102','42.3889167','-71.2208033')");
        db.execSQL("INSERT INTO Schedule Values('CS603-100','test@bentley.edu','Note1')");
//        Toast.makeText(getApplicationContext(), "Value inserted", Toast.LENGTH_LONG).show();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(remember.isChecked()){
                    try {
                        out = new OutputStreamWriter(openFileOutput("account.txt", MODE_PRIVATE));

                        out.write(email.getText().toString());
                        out.close();
                    }catch (IOException e) {}
                }
                account = email.getText().toString();
                cursor = db.rawQuery("SELECT Password FROM Users WHERE Email="+"\""+account+"\"",null);
                cursor.moveToFirst();
                str = cursor.getString(0);
////                count = cursor.getInt(cursor.getColumnIndex("Password"));

                if(password.getText().toString().equals(str)){
                    openStudentmenu();
                }
                else{
                    Toast.makeText(getApplicationContext(), "The Email Address and Password are not match", Toast.LENGTH_LONG).show();
                    password.setText("");
                }

            }
        });


        // insert records

        // write contents of Cursor to screen

    }

    public void openStudentmenu(){
        Intent i = new Intent(this, StudentMenuActivity.class);
        startActivity(i);
    }

}

