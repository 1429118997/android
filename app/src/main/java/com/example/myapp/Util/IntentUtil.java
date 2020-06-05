package com.example.myapp.Util;

import com.example.myapp.activity.LoginActivity;
import com.example.myapp.activity.MainActivity;

import android.content.Context;
import android.content.Intent;

public class IntentUtil {
    public static void activity(Context packageContext, Class<?> cls) {
    	Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(packageContext, cls);
		packageContext.startActivity(intent);
    }
}
