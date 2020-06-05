package com.example.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.myapp.R;
import com.example.myapp.bean.Order;
import com.example.myapp.dao.OrderDatabaseHelper;
import com.example.myapp.dialog.OrderOneDialog;
import com.example.myapp.dialog.OrderOneDialog.ButtonID;
import com.example.myapp.listener.MenuClickListener;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public boolean flag;

	public Order order;

	public ListView shopView;
	public Button mainBtn, orderBtn, muneBtn;
	public OrderDatabaseHelper databaseHelper = new OrderDatabaseHelper(MainActivity.this);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		shopView = (ListView) findViewById(R.id.list_view);
		mainBtn = (Button) findViewById(R.id.main_btn);
		orderBtn = (Button) findViewById(R.id.order_btn);
		muneBtn = (Button) findViewById(R.id.mune_btn);
		shopView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (flag == true) {
					ListView listView = (ListView) parent;
					View childAt = listView.getChildAt(position);
					TextView foodNameText = (TextView) childAt.findViewById(R.id.food_name);
					TextView foodPriceText = (TextView) childAt.findViewById(R.id.food_price);
					order.setFoodName(foodNameText.getText().toString());

					order.setPrice(foodPriceText.getText().toString());

					order.setImg((Integer)getFoodMap().get(position).get("照片"));
					final OrderOneDialog orderDialog = new OrderOneDialog(MainActivity.this);
					orderDialog.setTitle(foodNameText.getText().toString());
					orderDialog.show();
					orderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							if (orderDialog.mBtnClicked == ButtonID.BUTTON_OK) {
								// 存入数据库
								order.setCount(orderDialog.mNum);
//								Toast.makeText(MainActivity.this, order.toString(), Toast.LENGTH_LONG).show();
								SQLiteDatabase db = databaseHelper.getWritableDatabase();
								ContentValues values = new ContentValues();
								values.putNull("id");
								values.put("shop_name", order.getShopName());
								values.put("address", order.getAddress());
								values.put("food_name", order.getFoodName());
								values.put("price", order.getPrice());
								values.put("count", order.getCount());
								values.put("img", order.getImg());
								long id = db.insert("`order`", null, values);
								if (id != 0) {
									Toast.makeText(MainActivity.this, "已添加入购物车", Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(MainActivity.this, "购物失败", Toast.LENGTH_LONG).show();
								}

							}

						}
					});
				} else {
					TextView shopNameText = (TextView) view.findViewById(R.id.shopname);
					TextView shopAddrText = (TextView) view.findViewById(R.id.shop_address);
					order = new Order();
					order.setShopName(shopNameText.getText().toString());
					order.setAddress(shopAddrText.getText().toString());
//					Toast.makeText(MainActivity.this, order.toString(), Toast.LENGTH_LONG).show();
					SimpleAdapter foodAdapter = new SimpleAdapter(MainActivity.this, getFoodMap(), R.layout.food_item,
							new String[] { "照片", "菜名", "价格" },
							new int[] { R.id.food_image, R.id.food_name, R.id.food_price });
					shopView.setAdapter(foodAdapter);
					flag = true;
				}

			}
		});

		MenuClickListener menuClickListener = new MenuClickListener(MainActivity.this);
		mainBtn.setOnClickListener(menuClickListener);
		orderBtn.setOnClickListener(menuClickListener);
		muneBtn.setOnClickListener(menuClickListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShopList();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (flag == true) {
			updateShopList();
			flag = false;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void updateShopList() {
		ArrayList<Map<String, Object>> list = getInfo();
		SimpleAdapter shopListAdapter = new SimpleAdapter(this, list, R.layout.shop_item,
				new String[] { "照片", "店名", "地址" }, new int[] { R.id.shop_image, R.id.shopname, R.id.shop_address });
		shopView.setAdapter(shopListAdapter);

	}

	public ArrayList<Map<String, Object>> getInfo() {
		ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			if (i%3==0){
				hashMap.put("照片", R.raw.fooimage);
				hashMap.put("店名", "川巢老成都火锅" + i);
				hashMap.put("地址", "南山区招商路61-9号");
			}else if (i%3==1){
				hashMap.put("照片", R.raw.food1);
				hashMap.put("店名", "金达莱碳烤肉" + i);
				hashMap.put("地址", "学苑大道南科大雅苑1099号");
			}else {
				hashMap.put("照片", R.raw.food2);
				hashMap.put("店名", "维也纳斯" + i);
				hashMap.put("地址", "福田区八卦岭49栋1楼");
			}
			arrayList.add(hashMap);
		}
		return arrayList;
	}

	public ArrayList<Map<String, Object>> getFoodMap() {
		ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			if (i%3==0){
				hashMap.put("照片", R.raw.food);
				hashMap.put("菜名", "水晶火锅");
				hashMap.put("价格", "33.0");
			}else if (i%3==1){
				hashMap.put("照片", R.raw.foodname1);
				hashMap.put("菜名", "麻辣火锅");
				hashMap.put("价格", "56.0");
			}else {
				hashMap.put("照片", R.raw.foodname2);
				hashMap.put("菜名", "虾肠粉");
				hashMap.put("价格", "19.0");
			}

			arrayList.add(hashMap);
		}
		return arrayList;
	}

}
