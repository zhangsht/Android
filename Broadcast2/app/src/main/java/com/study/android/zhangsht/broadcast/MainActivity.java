package com.study.android.zhangsht.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Button staticButton = (Button) findViewById(R.id.staRegister);
        staticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, StaticActivity.class);
                startActivity(intent);
            }
        });

        Button dynamicButton = (Button) findViewById(R.id.dynRegister);
        dynamicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DynamicActivity.class);
                startActivity(intent);
            }
        });
    }
}
