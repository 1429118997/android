package com.example.myapp.activity;

import java.util.HashMap;

import com.example.myapp.R;
import com.example.myapp.Util.IntentUtil;
import com.example.myapp.dao.LoginDatabaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public String username;
	public String password;
	public Button loginBtn, registerBtn;

	public LoginDatabaseHelper loginDatebaseHelper=new LoginDatabaseHelper(LoginActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		String username = sharedPreferences.getString("username", null);
		String password = sharedPreferences.getString("password", null);
		if (username != null) {
			SQLiteDatabase db = loginDatebaseHelper.getReadableDatabase();
			Cursor cursor = db.query("user_tb", new String[] {"username","password"}, "username=? and password=?",new String[] {username,password} , null, null, null, null);
			if (cursor.getCount()==0) {
				Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
			} else {
				IntentUtil.activity(LoginActivity.this, MainActivity.class);
			}
		}

		final EditText loginUserName = (EditText) findViewById(R.id.login_username);
		final EditText loginPassWord = (EditText) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_login_btn);
		registerBtn = (Button) findViewById(R.id.login_register_btn);

		Button.OnClickListener clickListener = new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.login_login_btn:
					String username = loginUserName.getText().toString();
					String password = loginPassWord.getText().toString();
					SQLiteDatabase db = loginDatebaseHelper.getReadableDatabase();
					Cursor cursor = db.query("user_tb", new String[] {"username","password"}, "username=? and password=?",new String[] {username,password} , null, null, null, null);
					if (cursor.getCount()==0) {
						Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();

					} else {
						SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
						Editor edit = sharedPreferences.edit();
						edit.putString("username", username);
						edit.putString("password", password);
						edit.commit();
						IntentUtil.activity(LoginActivity.this, MainActivity.class);
					}
					break;
				case R.id.login_register_btn:
					Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
					startActivity(intent);
				}

			}

		};
		loginBtn.setOnClickListener(clickListener);
		registerBtn.setOnClickListener(clickListener);

	}

}
