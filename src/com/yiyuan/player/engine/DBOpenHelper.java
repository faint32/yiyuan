package com.yiyuan.player.engine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private String TABLE_NAME = "yiyuan.db";

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, "yiyuan.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table "
				+ TABLE_NAME 
				+ "(comment varchar(100), commentURL varchar(100), domain varchar(100), expiredDate integer, name varchar(100), path varchar(500), ports varchar(100), "
				+ "value varchar(200), version biginteger);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
