package com.example.mrarabwarfare.lab1;

import android.content.ContentValues;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.content.Context;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    ListView listViewChat;
    EditText editTextChat;
    Button sendChatButton;
    ChatAdapter messageAdapter;
    TextView message;
    private SQLiteDatabase database;
    private ChatDatabaseHelper dbHelper;

    ArrayList<String> chatArray = new ArrayList<String>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listViewChat = (ListView) findViewById(R.id.listViewChat);
        editTextChat = (EditText) findViewById(R.id.editTextChat);
        sendChatButton = (Button) findViewById(R.id.sendButton);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        messageAdapter = new ChatAdapter(this);
        listViewChat.setAdapter(messageAdapter);

        dbHelper = new ChatDatabaseHelper(this);
        //object of ChatDatabaseHelper

        //open db
        database = dbHelper.getWritableDatabase();
        //dbHelper.onCreate(database);

        if(database.isOpen()){
            Log.i("ChatActivity","Db is OPEN");
        }


        Cursor cursor = database.rawQuery("select chat_id, message from chat;",null);
        cursor.moveToFirst();



        while (!cursor.isAfterLast()) {
            Log.i("ChatActivity", "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i("ChatActivity", "Cursor's column count= " + cursor.getColumnCount());
            chatArray.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();


        }
        cursor.close();

        for (int i = 0; i <= cursor.getColumnCount(); i++) {
           // Log.i("Column Name", + cursor.getColumnName(i));

        }
        Log.i("ChatActivity","Chatwindow onCREATE()");
    }
    //onClick send button
    public void chatArray(View view) {
        //store text from edit to string and then to ArrayList
        String chatMsg = editTextChat.getText().toString();
        chatArray.add(chatMsg);

        ContentValues chatMessage = new ContentValues();
        chatMessage.put(ChatDatabaseHelper.KEY_MESSAGE,chatMsg);
        database.insert(ChatDatabaseHelper.CHAT_TABLE,null,chatMessage);


        //Restarts the process of getCount() getView()
        messageAdapter.notifyDataSetChanged();
        //Clear edit text
        editTextChat.setText("");
        Log.i("ChatActivity","in SEND button");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("ChatActivity", "In onDestroy()");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Chat Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mrarabwarfare.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Chat Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.mrarabwarfare.lab1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //inner class to handle switching views
    private class ChatAdapter extends ArrayAdapter<String> {


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatActivity.this.getLayoutInflater();

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }

        @Override
        public String getItem(int position) {

            return chatArray.get(position);
        }


        @Override
        public int getCount() {

            return chatArray.size();
        }

        //constructor to take context parameter
        public ChatAdapter(Context ctx) {
            super(ctx, 0);

        }


    }


}
