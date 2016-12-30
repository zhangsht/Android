package com.study.android.zhangsht.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhangsht on 2016/10/20.
 */

public class DynamicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_activity);

        final Button registButton = (Button) findViewById(R.id.registerButton),
                sendButton = (Button) findViewById(R.id.sendButton);
        registButton.setText("Register Broadcast");
        sendButton.setText("Send");
        final TextInputLayout editText = (TextInputLayout) findViewById(R.id.dynamicReceiverText);
        final EditText edit = (EditText) findViewById(R.id.dynamicReceiver);

        final DynamicReceiver dynamicReceiver = new DynamicReceiver();
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(edit.getText().toString(), "")) {
                    editText.setErrorEnabled(false);
                    if (TextUtils.equals(registButton.getText().toString(), "Register Broadcast")) {
                        IntentFilter dynamic_filter = new IntentFilter();
                        dynamic_filter.addAction("DynamicAction");
                        registerReceiver(dynamicReceiver, dynamic_filter);
                        registButton.setText("Unregister Broadcast");
                    } else if (TextUtils.equals(registButton.getText().toString(), "Unregister Broadcast")) {
                        unregisterReceiver(dynamicReceiver);
                        registButton.setText("Register Broadcast");
                    }
                } else {
                    editText.setErrorEnabled(true);
                    editText.setError("广播信息为空");
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(registButton.getText().toString(), "Register Broadcast")) {
                    Intent intent = new Intent("DynamicAction");
                    intent.setClass(DynamicActivity.this, DynamicReceiver.class);
                    intent.putExtra("text", edit.getText().toString());
                    sendBroadcast(intent);
                }
            }
        });

    }
}
