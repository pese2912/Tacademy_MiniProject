package com.example.tacademy.miniproject.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.tacademy.miniproject.MyApplication;
import com.example.tacademy.miniproject.login.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class DataManager  extends SQLiteOpenHelper{
    private static DataManager instance;
    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    private static final String DB_NAME = "chat";
    private static final int DB_VERSION = 1;

    private DataManager(){
        super(MyApplication.getContext(), DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE" + DataConstant.ChatUserTable.TABLE_NAME +" (" +
                DataConstant.ChatUserTable._ID +" INTEGER PRIMARY KEY AUTOINCREMENT"+
                DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID + " INTEGER,"+
                DataConstant.ChatUserTable.COLUMN_NAME+" TEXT," +
                DataConstant.ChatUserTable.COLUMN_EMAIL + " TEXT," +
                DataConstant.ChatUserTable.COLUMN_LAST_MESSAGE + " INTEGER);";

        db.execSQL(query);

        query = "CREATE TABLE"+ DataConstant.ChatTable.TABLE_NAME + " ("+
                DataConstant.ChatTable._ID + "INTEGER PRIMARY KEY AUTOINCREMENT"+
                DataConstant.ChatTable.COLUMN_USER_ID + " INTEGER,"+
                DataConstant.ChatTable.COLUMN_TYPE + " INTEGER,"+
                DataConstant.ChatTable.COLUMN_MESSAGE + " TEXT," +
                DataConstant.ChatTable.COLUMN_DATE + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ContentValues values= new ContentValues();

    public long getUserTableId(User user){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {DataConstant.ChatUserTable._ID};
        String selection = DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID + " =?";
        String[] selectionArgs = {"" + user.id};
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns, selection, selectionArgs, null ,null, null);
        if(c.moveToNext()){
            long id = c.getLong(c.getColumnIndex(DataConstant.ChatUserTable._ID));
            c.close();
            return id;
        }
        db = getWritableDatabase();
        values.clear();
        values.put(DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID, user.id);
        values.put(DataConstant.ChatUserTable.TABLE_NAME, user.userName);
        values.put(DataConstant.ChatUserTable.COLUMN_EMAIL , user.email);
        long id = db.insert(DataConstant.ChatUserTable.TABLE_NAME,null,values);

        return id;
    }

    public long addChatMessage(long uid, int type, String message, String date){
        SQLiteDatabase db = getWritableDatabase();
        values.clear();
        values.put(DataConstant.ChatTable.COLUMN_USER_ID, uid);
        values.put(DataConstant.ChatTable.COLUMN_TYPE, type);
        values.put(DataConstant.ChatTable.COLUMN_MESSAGE, message);
        if(!TextUtils.isEmpty(date)){
            date = convertDataString(new Date());
        }
        values.put(DataConstant.ChatTable.COLUMN_DATE, date);
        return db.insert(DataConstant.ChatTable.TABLE_NAME,null,values);
    }

    public Cursor getChatUserList(){
        String[] columns = {DataConstant.ChatUserTable._ID, DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID, DataConstant.ChatUserTable.COLUMN_NAME, DataConstant.ChatUserTable.COLUMN_EMAIL};
        SQLiteDatabase db= getReadableDatabase();
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME, columns,null,null,null,null,null);
        return c;
    }

    public String getLastDate(long serverid){
        SQLiteDatabase db =getReadableDatabase();
        String selection = DataConstant.ChatUserTable.COLUMN_SERVER_USER_ID + " =?";
        String[]columns = {DataConstant.ChatUserTable._ID};
        String[] selectionArgs = {"" + serverid};
        Cursor c = db.query(DataConstant.ChatUserTable.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        if(!c.moveToNext()){
            c.close();
            return null;
        }
        long id = c.getLong(c.getColumnIndex(DataConstant.ChatUserTable.COLUMN_NAME));
        c.close();

        columns = new String[]{DataConstant.ChatTable.COLUMN_DATE};
        selection = DataConstant.ChatTable.COLUMN_USER_ID + " =?";
        selectionArgs = new String[]{"" + id};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE+" DESC";
        String limit = "1";
        c = db.query(DataConstant.ChatTable.TABLE_NAME,columns,selection,selectionArgs,null,null,orderBy,limit);
        if(!c.moveToNext()){
            c.close();
            return  null;
        }
        String date = c.getString(c.getColumnIndex(DataConstant.ChatTable.TABLE_NAME));
        return date;
    }

    public Cursor getChatList(long userid){
        String[] columns = {DataConstant.ChatTable._ID,DataConstant.ChatTable.COLUMN_MESSAGE, DataConstant.ChatTable.COLUMN_TYPE, DataConstant.ChatTable.COLUMN_DATE};
        String selection = DataConstant.ChatTable.COLUMN_USER_ID + "=?";
        String[] selectionArgs = {"" + userid};
        String orderBy = DataConstant.ChatTable.COLUMN_DATE + " ASC";
        SQLiteDatabase db= getReadableDatabase();
        Cursor c= db.query(DataConstant.ChatTable.TABLE_NAME, columns, selection, selectionArgs,null,null,orderBy);
        return c;

    }

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM--dd'T'HH:mm:SS");//서버와 동일한 타입으로 규정
    public String convertDataString(Date date){
        return dateFormat.format(date);

    }
}
