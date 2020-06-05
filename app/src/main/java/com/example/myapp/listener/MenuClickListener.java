package com.example.myapp.listener;

import com.example.myapp.R;
import com.example.myapp.Util.IntentUtil;
import com.example.myapp.activity.MainActivity;
import com.example.myapp.activity.MenuActivity;
import com.example.myapp.activity.OrderNewActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MenuClickListener implements View.OnClickListener {

	private Context context;

	public MenuClickListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn:
			IntentUtil.activity(context, MainActivity.class);
			break;
		case R.id.order_btn:
			IntentUtil.activity(context, OrderNewActivity.class);

			break;
		case R.id.mune_btn:
			IntentUtil.activity(context, MenuActivity.class);
			break;
		default:
			break;
		}
	}

}
