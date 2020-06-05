package com.example.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myapp.R;
import com.example.myapp.dao.OrderDatabaseHelper;
import com.example.myapp.listener.MenuClickListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OrderOldActivity extends Activity  {

	public Button beforeBtn,afterBtn;
	public Button mainBtn, orderBtn, muneBtn;

	public ListView oldOrderView;

	public OrderDatabaseHelper orderDatabaseHelper=new OrderDatabaseHelper(OrderOldActivity.this);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_old);
		beforeBtn=(Button)findViewById(R.id.before_btn);
		afterBtn=(Button)findViewById(R.id.after_btn);
		mainBtn = (Button) findViewById(R.id.main_btn);
		orderBtn = (Button) findViewById(R.id.order_btn);
		muneBtn = (Button) findViewById(R.id.mune_btn);
		oldOrderView=(ListView)findViewById(R.id.list_view2);

		OrderClickListener orderClickListener=new OrderClickListener();
		beforeBtn.setOnClickListener(orderClickListener);
		afterBtn.setOnClickListener(orderClickListener);

		MenuClickListener menuClickListener = new MenuClickListener(OrderOldActivity.this);
		mainBtn.setOnClickListener(menuClickListener);
		orderBtn.setOnClickListener(menuClickListener);
		muneBtn.setOnClickListener(menuClickListener);

	}




	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	public void updateList() {
		ArrayList<HashMap<String, Object>> array = getList();
		SimpleAdapter foodAdapter = new SimpleAdapter(OrderOldActivity.this, array, R.layout.order_old_item,
				new String[] { "id", "shop_name", "food_name", "title", "img","date" },
				new int[] { R.id.order_id, R.id.shop_name, R.id.food_name, R.id.title, R.id.food_image,R.id.date });
		oldOrderView.setAdapter(foodAdapter);
	}

	public ArrayList<HashMap<String, Object>> getList() {
		SQLiteDatabase db = orderDatabaseHelper.getReadableDatabase();
		Cursor custer = db.query("`order`", new String[] { "id", "shop_name", "food_name", "price", "count", "date","img" },
				"flag=?", new String[] { "1" }, null, null, null);
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

		while (custer.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", custer.getInt(0));
			map.put("shop_name", custer.getString(1));
			map.put("food_name", custer.getString(2));
			float price = custer.getFloat(3);
			int count = custer.getInt(4);
			float money = price * count;
			map.put("title", price + " x " + count + "=" + money);
			map.put("date", custer.getString(5));
			map.put("img", custer.getInt(6));
			arrayList.add(map);
		}

		return arrayList;
	}



	class OrderClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.before_btn:
					Intent intent1 = new Intent(OrderOldActivity.this, OrderNewActivity.class);
					startActivity(intent1);
					break;
				case R.id.after_btn:
					Intent intent2 = new Intent(OrderOldActivity.this, OrderOldActivity.class);
					startActivity(intent2);
					break;

			}
		}

	}

}
