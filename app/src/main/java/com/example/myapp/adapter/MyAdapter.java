package com.example.myapp.adapter;

import java.util.List;
import java.util.Map;

import com.example.myapp.R;
import com.example.myapp.activity.MainActivity;
import com.example.myapp.activity.OrderNewActivity;
import com.example.myapp.dao.OrderDatabaseHelper;
import com.example.myapp.inf.ActivityToUpdateUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class MyAdapter extends SimpleAdapter {

	private Context context;

	private List<Map<String, Object>> data;

	private int resource;

	private int[] to;

	private OrderDatabaseHelper orderDatabaseHelper;

	private String[] from;

	private int position;

	private ActivityToUpdateUI activityToUpdateUI;

	public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ActivityToUpdateUI activityToUpdateUI) {
		super(context, data, resource, from, to);
		this.context = context;
		this.data = (List<Map<String, Object>>) data;
		this.resource = resource;
		this.to = to;
		this.from = from;
		orderDatabaseHelper = new OrderDatabaseHelper(context);
		this.activityToUpdateUI=activityToUpdateUI;
	}

	@Override
	public ViewBinder getViewBinder() {
		// TODO Auto-generated method stub
		return super.getViewBinder();
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.position = position;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(this.resource, parent, false);

		Button button = (Button) convertView.findViewById(R.id.order_cancel_btn);
		button.setOnClickListener(new CancelClickListener(position));

		return super.getView(position, convertView, parent);
	}


	class ViewHolder{
		Button button;
	}

	class CancelClickListener implements OnClickListener {

		public int position;

		CancelClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder = new AlertDialog.Builder(MyAdapter.this.context);
			builder.setTitle("提示").setMessage("是否取消该订单").setNegativeButton("否", null)
					.setPositiveButton("是", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							SQLiteDatabase db = orderDatabaseHelper.getWritableDatabase();
							db.delete("`order`", "id=?",
									new String[] { data.get(MyAdapter.this.position).get("id").toString() });
							activityToUpdateUI.updateUI();
						}

					}).create().show();

		}
	}

}
