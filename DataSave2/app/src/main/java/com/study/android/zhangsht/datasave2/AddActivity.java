package com.study.android.zhangsht.datasave2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zhangsht on 2016/11/20.
 */

public class AddActivity extends AppCompatActivity {
    private EditText name;
    private  EditText birth;
    private EditText gift;
    private Button button;
    private  MyDB myDB = new MyDB(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);

        name = (EditText)findViewById(R.id.addName);
        birth = (EditText)findViewById(R.id.addBirth);
        gift = (EditText)findViewById(R.id.addGift);
        button = (Button)findViewById(R.id.addButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String birthText = birth.getText().toString();
                String giftText = gift.getText().toString();
                if (nameText.equals("")) {
                    Toast.makeText(AddActivity.this, "名字为空,请完善", Toast.LENGTH_SHORT).show();
                } else {
                    if (myDB.isExists(nameText)) {
                        Toast.makeText(AddActivity.this, "此人备忘录已经存在", Toast.LENGTH_SHORT).show();
                    } else {
                        myDB.insert2DB(nameText, birthText, giftText);
                        Intent intent = new Intent();
                        intent.setClass(AddActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
