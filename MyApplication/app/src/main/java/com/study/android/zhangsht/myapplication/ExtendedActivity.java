package com.study.android.zhangsht.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by zhangsht on 2016/9/11.
 */
public class ExtendedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extended_layout);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);

        Button login_btn = (Button) findViewById(R.id.login),
                register_btn = (Button) findViewById(R.id.register);

        final TextInputLayout usernameText = (TextInputLayout) findViewById(R.id.usernameText),
                passwordText = (TextInputLayout) findViewById(R.id.passwordText);
        final EditText userName = usernameText.getEditText(),
                passWord = passwordText.getEditText();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(userName.getText().toString(), "")) {
                    usernameText.setErrorEnabled(true);
                    usernameText.setError("用户名为空");
                } else if (TextUtils.equals(passWord.getText().toString(), "")) {
                    passwordText.setErrorEnabled(true);
                    passwordText.setError("密码为空");
                } else if (!TextUtils.equals(userName.getText().toString(), "Android") || !TextUtils.equals(passWord.getText().toString(), "123456")) {
                    Snackbar.make(linearLayout, "登陆失败", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar.make(linearLayout, "确定按钮点击", Snackbar.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    usernameText.setErrorEnabled(false);
                    passwordText.setErrorEnabled(false);
                } else {
                    Snackbar.make(linearLayout, "登陆成功", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar.make(linearLayout, "确定按钮点击", Snackbar.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    usernameText.setErrorEnabled(false);
                    passwordText.setErrorEnabled(false);
                }
            }
        });

        final RadioButton studentRadio = (RadioButton) findViewById(R.id.student),
                teacherRadio = (RadioButton) findViewById(R.id.teacher),
                leagueRadio = (RadioButton) findViewById(R.id.league),
                managerRadio = (RadioButton) findViewById(R.id.manager);

        final RadioGroup orgRadioGroup = (RadioGroup) findViewById(R.id.orgnazation);
        orgRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            RadioButton storeRadio = new RadioButton(ExtendedActivity.this);

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == studentRadio.getId()) {
                    storeRadio = studentRadio;
                } else if (checkedId == teacherRadio.getId()) {
                    storeRadio = teacherRadio;
                } else if (checkedId == leagueRadio.getId()) {
                    storeRadio = leagueRadio;
                } else if (checkedId == managerRadio.getId()) {
                    storeRadio = managerRadio;
                }
                Snackbar.make(linearLayout, storeRadio.getText().toString() + "身份被选中", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(linearLayout, "确定按钮点击", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedButton = (RadioButton) findViewById(orgRadioGroup.getCheckedRadioButtonId());
                Snackbar.make(linearLayout, checkedButton.getText().toString() + "身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(linearLayout, "确定按钮点击", Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
}
