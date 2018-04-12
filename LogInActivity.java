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
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    private Button login;
    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor cursor;
    private EditText email;
    private EditText password;
    private int count;
    private String str;
    private ContactsContract.Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        db = openOrCreateDatabase("CSBS.db",
                Context.MODE_PRIVATE, null);
        db.setLocale(Locale.getDefault());
        db.execSQL("Drop TABLE IF EXISTS 'Users'");
        db.execSQL("Create TABLE IF not EXISTS 'Users'('Email' Varchar(30) not null, 'Password' Varchar(30) not null)");
//        Toast.makeText(getApplicationContext(), "Table created", Toast.LENGTH_LONG).show();

//        db.execSQL("Create Table Users");
        db.execSQL("INSERT INTO Users Values('test@bentley.edu','12345')");
//        Toast.makeText(getApplicationContext(), "Value inserted", Toast.LENGTH_LONG).show();


        login = findViewById(R.id.loginbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.execSQL("SELECT Password FROM Users WHERE Email=?", new String[]{email.getText().toString()});
                cursor = db.rawQuery("SELECT Password FROM Users WHERE Email="+"\""+email.getText().toString()+"\"",null);
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

