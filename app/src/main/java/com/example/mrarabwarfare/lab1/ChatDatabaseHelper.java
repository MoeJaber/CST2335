package com.example.mrarabwarfare.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MoeJaber on 2016-10-10.
 */
public class ChatDatabaseHelper extends SQLiteOpenHelper
{

    public final static String DATABASE_NAME = "Chats.db";
    public final static int VERSION_NUM = 2;
    public final static String CHAT_TABLE = "chat";
    public final static String KEY_ID= "chat_id";
    public final static String KEY_MESSAGE = "message";

    private final static String DATABASE_CREATE = "create table " + CHAT_TABLE + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";

    public ChatDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME, null,VERSION_NUM);
        Log.i("ChatDatabaseHelper", "Calling onCreate");

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

}
