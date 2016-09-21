package com.example.mrarabwarfare.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    Button startButton;
    static final int START_REQUEST = 5;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        //reference to button by ID
        startButton = (Button) findViewById(R.id.button);

        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent startActivityIntent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(startActivityIntent,START_REQUEST);
                onActivityResult(START_REQUEST,RESULT_OK,startActivityIntent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == 5 && resultCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this , "ListItemsActivity passed:" + messagePassed, duration);
            toast.show(); //display your message box
        }


    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

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
}
