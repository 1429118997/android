package com.example.myapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDatabaseHelper extends SQLiteOpenHelper  {

	public final static String name="myapp.db";
	public final static int version=1;
	
	public OrderDatabaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS `order`" + 
				"(" + 
				"   id INTEGER PRIMARY KEY," + 
				"   shop_name VARCHAR(30)," + 
				"   address VARCHAR(255)," + 
				"   food_name VARCHAR(30)," + 
				"   price  DECIMAL(3,2)," + 
				"   `count` INTEGER,"+
				"   `date`  varchar(30),"+
				"   img INTEGER," + 
				"   flag INTEGER DEFAULT 0" + 
				") ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	

}
