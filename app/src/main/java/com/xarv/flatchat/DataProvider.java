package com.xarv.flatchat;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;


public class DataProvider extends ContentProvider {
    private DbHelper dbHelper;
    private UriMatcher mUrimatcher;
    private String authorities= "com.xarv.flatchat";

    private final static int GET_MESSAGES = 1;
    private Uri msgTable = Uri.parse("content://" + authorities+"/content"  + "/MSG_TABLE");

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean successInsert ;
        successInsert = dbHelper.insertMsgTable("1","1","Hello, how are you ?","0","default");
        successInsert = dbHelper.insertMsgTable("2", "2", "http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg", "1", "default");
        successInsert = dbHelper.insertMsgTable("3", "3", "How is weather ?", "0", "default");
        successInsert = dbHelper.insertMsgTable("4", "4", "http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg", "1", "default");
        successInsert = dbHelper.insertMsgTable("5", "5", "http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg", "1", "default");
        successInsert = dbHelper.insertMsgTable("6", "6", "Tomorrow is sunday", "0", "default");
        successInsert = dbHelper.insertMsgTable("7", "7", "To define one such view, you need to specify it an Android Context. This is usually the Activity where the tabs will be displayed. Supposing that you initialize your tabs in an Activity, simply pass the Activity instance as a Context", "0", "default");
        successInsert = dbHelper.insertMsgTable("8", "8", "http://media.mediatemple.netdna-cdn.com/wp-content/uploads/2013/01/3.jpg", "1", "default");
        mUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUrimatcher.addURI( authorities,"/content"  + "/MSG_TABLE",1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = dbHelper.getMessages();

//        switch (mUrimatcher.match(uri)) {
//            case GET_MESSAGES:
//                Cursor cursor = dbHelper.getReadableDatabase().query("MSG_TABLE", projection,
//                null, null, null, null, null);
//                return cursor;
//        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }



    public class DbHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME="storage.db";


        //MESSAGE TABLE
        private static final String ID = "_id";
        private static final String MESSAGE_TABLE = "MSG_TABLE";
        private static final String MESSAGE_ID = "MSG_ID";
        private static final String MESSAGE_TYPE ="MSG_TYPE";
        private static final String MESSAGE_DATA = "MSG_DATA";
        private static final String MESSAGE_TIMESTAMP = "MSG_TIMESTAMP";

        public final String[] MESSAGE_COL={"_id","MSG_ID","MSG_TYPE","MSG_DATA","MSG_TIMESTAMP"};

        private static final String MESSAGE_TABLE_CREATE=
                "CREATE TABLE " + MESSAGE_TABLE +" ( " +
                        ID + " TEXT NOT NULL,"+
                        MESSAGE_ID + " TEXT PRIMARY KEY NOT NULL, " +
                        MESSAGE_TYPE + " TEXT NOT NULL, " +
                        MESSAGE_DATA + " TEXT NOT NULL, " +
                        MESSAGE_TIMESTAMP + " TEXT NOT NULL " +
                        ");";


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MESSAGE_TABLE_CREATE);


        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // upgrade
        }
        public boolean  insertMsgTable(String id,String msgId, String msgData,String msgType,String msgTime)
        {
            try {

                SQLiteDatabase database=  this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ID,id);
                values.put(MESSAGE_ID, msgId);
                values.put(MESSAGE_DATA, msgData);
                values.put(MESSAGE_TYPE, msgType);
                values.put(MESSAGE_TIMESTAMP, msgTime);

                Long rowID = database.insertWithOnConflict(MESSAGE_TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);

                if(rowID < 0)
                    return false;
                else
                    return true;
            } catch (Exception e) {
                return false;

            }

        }

        public Cursor getMessages(){
            Cursor cursor = getReadableDatabase().rawQuery("select * from MSG_TABLE",null);
            return cursor;
        }




    }
}
