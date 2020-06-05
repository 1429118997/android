package com.example.myapp.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDatabaseHelper extends SQLiteOpenHelper {

	public final static String name="userinfo.db";
	
	public final static int version=1;
	
	public LoginDatabaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE user_tb" + 
				"(" + 
				"   id INTEGER PRIMARY KEY," + 
				"   username VARCHAR(30)," + 
				"   `password` VARCHAR(30)," + 
				"   email VARCHAR(30)" + 
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
