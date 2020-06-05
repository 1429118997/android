package com.example.myapp.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.example.myapp.R;
import com.example.myapp.adapter.MyAdapter;
import com.example.myapp.dao.OrderDatabaseHelper;
import com.example.myapp.dialog.OrderOneDialog;
import com.example.myapp.dialog.OrderOneDialog.ButtonID;
import com.example.myapp.inf.ActivityToUpdateUI;
import com.example.myapp.listener.MenuClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class OrderNewActivity extends Activity implements ActivityToUpdateUI {

	public Button mainBtn, orderBtn, muneBtn;
	public Button beforeBtn, afterBtn;
	public Button paymentBtn, cancelBtn;

	public ListView listView;

	public LinearLayout menuView;
	//
	public OrderDatabaseHelper orderDatabaseHelper = new OrderDatabaseHelper(OrderNewActivity.this);
	//	final OrderOneDialog orderDialog = new OrderOneDialog(OrderNewActivity.this);
//
	public static float total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_new);
		mainBtn = (Button) findViewById(R.id.main_btn);
		orderBtn = (Button) findViewById(R.id.order_btn);
		muneBtn = (Button) findViewById(R.id.mune_btn);
		beforeBtn = (Button) findViewById(R.id.before_btn);
		afterBtn = (Button) findViewById(R.id.after_btn);
		paymentBtn = (Button) findViewById(R.id.payment_btn);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		listView = (ListView) findViewById(R.id.list_view1);
		menuView = (LinearLayout) findViewById(R.id.mune_view2);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ArrayList<HashMap<String, Object>> list = getList();
				OrderOneDialog orderDialog=new OrderOneDialog(OrderNewActivity.this);
				TextView tvOrderNum = (TextView)orderDialog.findViewById(R.id.tvOrderNum);
				System.out.println(tvOrderNum);
				orderDialog.setTitle("修改订单");
				System.out.println(list);
				System.out.println(list.get(position).get("count"));
				tvOrderNum.setText(""+list.get(position).get("count"));
				orderDialog.show();
				orderDialog.setOnDismissListener(new UpdateDismissListener(list, position,orderDialog));
			}
		});

		PaymentClickListenr paymentClickListenr = new PaymentClickListenr();
		paymentBtn.setOnClickListener(paymentClickListenr);
		cancelBtn.setOnClickListener(paymentClickListenr);

		OrderClickListener orderClickListener = new OrderClickListener();
		beforeBtn.setOnClickListener(orderClickListener);
		afterBtn.setOnClickListener(orderClickListener);
		MenuClickListener menuClickListener = new MenuClickListener(OrderNewActivity.this);
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
		MyAdapter foodAdapter = new MyAdapter(OrderNewActivity.this, array, R.layout.order_item,
				new String[] { "id", "shop_name", "food_name", "title", "img" },
				new int[] { R.id.order_id, R.id.shop_name, R.id.food_name, R.id.price_one_tx, R.id.food_image }, this);
		listView.setAdapter(foodAdapter);
		if (this.total > 0) {
			TextView priceView = (TextView) findViewById(R.id.price_view);
			priceView.setText("" + this.total);
			menuView.setVisibility(View.VISIBLE);
		} else {
			menuView.setVisibility(View.GONE);
		}
	}

	public ArrayList<HashMap<String, Object>> getList() {
		SQLiteDatabase db = orderDatabaseHelper.getReadableDatabase();
		Cursor custer = db.query("`order`", new String[] { "id", "shop_name", "food_name", "price", "count", "img" },
				"flag=?", new String[] { "0" }, null, null, null);
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

		this.total = 0;
		while (custer.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", custer.getInt(0));
			map.put("shop_name", custer.getString(1));
			map.put("food_name", custer.getString(2));
			float price = custer.getFloat(3);
			int count = custer.getInt(4);
			float money = price * count;
			map.put("title", price + " x " + count + "=" + money);
			map.put("count", count);
			map.put("img", custer.getInt(5));
			arrayList.add(map);
			this.total = this.total + money;
		}

		return arrayList;
	}

	@Override
	public void updateUI() {
		this.onResume();
	}

	class UpdateDismissListener implements DialogInterface.OnDismissListener {

		private ArrayList<HashMap<String, Object>> list;
		private int position;
		private OrderOneDialog orderDialog;

		public UpdateDismissListener(ArrayList<HashMap<String, Object>> list, int position,OrderOneDialog orderDialog) {
			this.list = list;
			this.position = position;
			this.orderDialog=orderDialog;
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			if (orderDialog.mBtnClicked == ButtonID.BUTTON_OK) {
				SQLiteDatabase db = orderDatabaseHelper.getWritableDatabase();
				if (orderDialog.mNum <= 0) {
					Toast.makeText(OrderNewActivity.this, "操作错误，请检查操作", Toast.LENGTH_LONG).show();
				} else {
					ContentValues values = new ContentValues();
					values.put("count", orderDialog.mNum);
					int update = db.update("`order`", values, "id=?",
							new String[] {  list.get(position).get("id").toString() });
					if (update != 0) {
						Toast.makeText(OrderNewActivity.this, "修改成功", Toast.LENGTH_LONG).show();
						updateList();
					} else {
						Toast.makeText(OrderNewActivity.this, "修改失败", Toast.LENGTH_LONG).show();
					}
				}

			}
		}

	}

	class PaymentClickListenr implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.payment_btn) {
				AlertDialog.Builder builder = new AlertDialog.Builder(OrderNewActivity.this);
				builder.setTitle("请确定支付").setMessage("是否支付" + OrderNewActivity.total).setNegativeButton("取消", null)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								SQLiteDatabase db = orderDatabaseHelper.getWritableDatabase();
								ContentValues values = new ContentValues();
								values.put("flag", 1);
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								values.put("date", simpleDateFormat.format(new Date()));
								db.update("`order`", values, "flag=?", new String[] { "0" });
								updateList();
							}
						}).create().show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(OrderNewActivity.this);
				builder.setTitle("请确定").setMessage("是否取消所有订单").setNegativeButton("否", null).setPositiveButton("是",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase db = orderDatabaseHelper.getWritableDatabase();
								db.delete("`order`", "flag=?", new String[] { "0" });
								updateList();

							}

						}).create().show();;
			}
		}

	}

	class OrderClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.before_btn:
					Intent intent1 = new Intent(OrderNewActivity.this, OrderNewActivity.class);
					startActivity(intent1);
					break;
				case R.id.after_btn:
					Intent intent2 = new Intent(OrderNewActivity.this, OrderOldActivity.class);
					startActivity(intent2);
					break;

			}
		}

	}
}
