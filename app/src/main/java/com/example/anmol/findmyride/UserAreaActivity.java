package com.example.anmol.findmyride;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {
    SharedPreferences sf;
    public static final String preference="pref";
    public static  String saveIt="saveKey";
    public static  String save="saveKey";
    Databasehelper helper= new Databasehelper(this);
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        sf = getSharedPreferences(preference,Context.MODE_PRIVATE);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomemsg);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        final String phone=intent.getStringExtra("phone");

        helper.insertPhone(phone);

        String message = "Welcome "+name;
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(phone);
    }

}
