package com.study.android.zhangsht.contacts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zhangsht on 2016/10/18.
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        String[] opetations = new String[]{"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.detai_list, opetations);
        ListView listView = (ListView) findViewById(R.id.operation_list);
        listView.setAdapter(arrayAdapter);

        String name = "", phoneNumber = "", type = "", owerArea = "", bgColor = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            phoneNumber = extras.getString("phoneNumber");
            type = extras.getString("type");
            owerArea = extras.getString("homeArea");
            bgColor = extras.getString("bgColor");
        }

        System.out.print(phoneNumber);
        TextView nameText = (TextView) findViewById(R.id.detaiName);
        TextView phoneText = (TextView) findViewById(R.id.detailPhone);
        TextView thText = (TextView) findViewById(R.id.detailTH);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relator);
        nameText.setText(name);
        phoneText.setText(phoneNumber);
        thText.setText(type + "  " + owerArea);
        relativeLayout.setBackgroundColor(Color.parseColor(bgColor.toString()));

        ImageButton backButton = (ImageButton) findViewById(R.id.back);
        final ImageButton starredButton = (ImageButton) findViewById(R.id.starred);
        backButton.getBackground().setAlpha(0);
        starredButton.getBackground().setAlpha(0);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        starredButton.setOnClickListener(new View.OnClickListener() {
            boolean tag = false;

            @Override
            public void onClick(View view) {
                if (tag == false) {
                    starredButton.setImageResource(R.mipmap.full_star);
                    tag = true;
                } else {
                    starredButton.setImageResource(R.mipmap.empty_star);
                    tag = false;
                }
            }
        });
    }
}