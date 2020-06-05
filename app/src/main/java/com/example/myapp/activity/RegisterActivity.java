package com.example.myapp.activity;

import com.example.myapp.R;
import com.example.myapp.Util.IntentUtil;
import com.example.myapp.dao.LoginDatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	public String username,password,email;
	public Button registerBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		final EditText registerUsername = (EditText)findViewById(R.id.register_username);
		final EditText registerPassword = (EditText)findViewById(R.id.register_password);
		final EditText registerEmail = (EditText)findViewById(R.id.register_email);
		registerBtn=(Button)findViewById(R.id.register_register_btn);
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginDatabaseHelper loginDatabaseHelper = new LoginDatabaseHelper(RegisterActivity.this);
				SQLiteDatabase db = loginDatabaseHelper.getWritableDatabase();

				username=registerUsername.getText().toString();
				password=registerPassword.getText().toString();
				email=registerEmail.getText().toString();
				//判断是否已经注册
				Cursor custer = db.query("user_tb", new String[] {"*"}, "username=?", new String[] {username}, null, null, null);

				if(custer.getCount()>0) {
					Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT);
				}else {
					ContentValues values = new ContentValues();
					values.put("username", username);
					values.put("password", password);
					values.put("email", email);
					db.insert("user_tb", null, values);
					SharedPreferences sharedPreferences=getSharedPreferences("userInfo",  Context.MODE_PRIVATE);
					Editor edit = sharedPreferences.edit();
					edit.putString("username", username);
					edit.putString("password", password);
					edit.putString("email", email);
					edit.commit();
					IntentUtil.activity(RegisterActivity.this, MainActivity.class);
				}
			}
		});

	}

}
