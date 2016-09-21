package com.example.mrarabwarfare.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    //Object for Login edit text field
    EditText defaultEmailText;
    public static final String DEFAULT = "email@domain.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //find the login text field
        defaultEmailText = (EditText) findViewById(R.id.loginEditText);

        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");


        SharedPreferences sharedPreferences = getSharedPreferences("Data",Context.MODE_PRIVATE);
        String defaultEmail = sharedPreferences.getString("defaultEmail",DEFAULT);
        defaultEmailText.setText(defaultEmail);

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void save(View view){

        SharedPreferences sharedPreferences = getSharedPreferences("Data",Context.MODE_PRIVATE);
        //edit shared file to add strings
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //pull the value from edit text field EMAIL
        editor.putString("defaultEmail",defaultEmailText.getText().toString());
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);

        //Toast.makeText(this,"Reference Created",Toast.LENGTH_LONG).show();
    }

}
