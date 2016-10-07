package com.study.android.zhangsht.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);

        Button basic = (Button)findViewById(R.id.basic),
                extended = (Button)findViewById(R.id.extended);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GuideActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
       extended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GuideActivity.this, ExtendedActivity.class);
                startActivity(intent);
            }
        });
    }
}
