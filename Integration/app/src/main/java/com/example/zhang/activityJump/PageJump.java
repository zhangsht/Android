package com.example.zhang.activityJump;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhang.integration.R;

public class PageJump extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_jump);

        final EditText editText = findViewById(R.id.et_intentUse);
        Button btn = findViewById(R.id.btn_passResult);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String msg = editText.getText().toString();
                intent.putExtra("msg", msg);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
