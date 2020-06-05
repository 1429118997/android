package com.example.myapp.activity;

import android.widget.ImageButton;
import com.example.myapp.R;
import com.example.myapp.Util.IntentUtil;
import com.example.myapp.listener.MenuClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {

    public Button mainBtn, orderBtn, muneBtn;
    public Button infoBtn, addressBtn, phoneBtn, groupBtn;
    public ImageButton cancellationBtn;
    public TextView usernameTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mainBtn = (Button) findViewById(R.id.main_btn);
        orderBtn = (Button) findViewById(R.id.order_btn);
        muneBtn = (Button) findViewById(R.id.mune_btn);
        usernameTx = (TextView) findViewById(R.id.username_tx);
        infoBtn = (Button) findViewById(R.id.info_btn);
        addressBtn = (Button) findViewById(R.id.address_btn);
        phoneBtn = (Button) findViewById(R.id.phone_btn);
        groupBtn = (Button) findViewById(R.id.group_btn);
        cancellationBtn = (ImageButton) findViewById(R.id.cancellation_btn);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        usernameTx.setText(username);


        InfoClickListener infoClickListener = new InfoClickListener();
        infoBtn.setOnClickListener(infoClickListener);
        addressBtn.setOnClickListener(infoClickListener);
        phoneBtn.setOnClickListener(infoClickListener);
        groupBtn.setOnClickListener(infoClickListener);


        cancellationBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.clear();
                edit.commit();
                IntentUtil.activity(MenuActivity.this, LoginActivity.class);
            }
        });

        MenuClickListener menuClickListener = new MenuClickListener(MenuActivity.this);
        mainBtn.setOnClickListener(menuClickListener);
        orderBtn.setOnClickListener(menuClickListener);
        muneBtn.setOnClickListener(menuClickListener);
    }

    class InfoClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            switch (v.getId()) {
                case R.id.info_btn:

                    builder.setTitle("提示").setMessage("肝不动了0.0------->有缘在肝").show();
                    break;
                case R.id.address_btn:

                    builder.setTitle("提示").setMessage("你在心里想就好，别指望我帮你填").show();
                    break;
                case R.id.phone_btn:

                    builder.setTitle("提示").setMessage("邮箱1429118997@qq.com，电话就算了").show();
                    break;
                case R.id.group_btn:

                    builder.setTitle("提示").setMessage("团队，不存在的，这是我肝出来的，孩砸").show();
                    break;
                default:
                    break;
            }

        }

    }

}
