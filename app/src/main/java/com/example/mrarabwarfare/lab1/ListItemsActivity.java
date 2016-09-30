package com.example.mrarabwarfare.lab1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton camImage;
    Switch switchToggle;


    CharSequence text = "Switch is On";
    CharSequence textOff = "Switch is Off";
    int durationOff = Toast.LENGTH_LONG;
    int duration = Toast.LENGTH_SHORT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        //ImageButton find by ID to imageButton in design
        camImage = (ImageButton) findViewById(R.id.imageButton);
        //Create a onClick Listener
        camImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
                }
                //Send data to onActivityResult to handle image
                onActivityResult(REQUEST_IMAGE_CAPTURE, RESULT_OK, takePicIntent);
            }

        });
    }

    //store image in imageButton
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle thumbnail = data.getExtras();
            if (thumbnail != null) {
                Bitmap imageBitmap = (Bitmap) thumbnail.get("data");
                camImage.setImageBitmap(imageBitmap);
            }
        }
    }

    //Switch button onClick
    protected void setOnCheckedChanged(View v) {
        Switch switchButton = (Switch) v;
        if (switchButton.isChecked()) {
            Toast toast = Toast.makeText(this, text, duration); //this is the ListActivity
            toast.show(); //display your message box
        } else {
            Toast toast = Toast.makeText(this, textOff, durationOff); //this is the ListActivity
            toast.show(); //display your message box
        }
    }

    protected void checkBoxFinish(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response", "My information to share");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}

