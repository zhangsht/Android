package com.study.android.zhangsht.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by zhangsht on 2016/9/11.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Button login_btn = (Button) findViewById(R.id.login),
                register_btn = (Button) findViewById(R.id.register);

        final EditText userName = (EditText) findViewById(R.id.username),
                passWord = (EditText) findViewById(R.id.password);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "确定按钮点击", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消按钮点击", Toast.LENGTH_SHORT).show();
                    }
                });

                if (TextUtils.isEmpty(userName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "用户名为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passWord.getText().toString())) {
                    Toast.makeText(MainActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(userName.getText().toString(), "Android") || !TextUtils.equals(passWord.getText().toString(), "123456")) {
                    builder.setMessage("登陆失败");
                    builder.create().show();
                } else {
                    builder.setMessage("登陆成功");
                    builder.create().show();
                }
            }
        });

        final RadioButton studentRadio = (RadioButton) findViewById(R.id.student),
                teacherRadio = (RadioButton) findViewById(R.id.teacher),
                leagueRadio = (RadioButton) findViewById(R.id.league),
                managerRadio = (RadioButton) findViewById(R.id.manager);
        final RadioGroup orgRadioGroup = (RadioGroup) findViewById(R.id.orgnazation);
        orgRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == studentRadio.getId()) {
                    Toast.makeText(MainActivity.this, "学生身份被选中", Toast.LENGTH_SHORT).show();
                } else if (checkedId == teacherRadio.getId()) {
                    Toast.makeText(MainActivity.this, "教师身份被选中", Toast.LENGTH_SHORT).show();
                } else if (checkedId == leagueRadio.getId()) {
                    Toast.makeText(MainActivity.this, "社团身份被选中", Toast.LENGTH_SHORT).show();
                } else if (checkedId == managerRadio.getId()) {
                    Toast.makeText(MainActivity.this, "管理者身份被选中", Toast.LENGTH_SHORT).show();
                }
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedButton = (RadioButton) findViewById(orgRadioGroup.getCheckedRadioButtonId());
                Toast.makeText(MainActivity.this, checkedButton.getText().toString() + "身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
