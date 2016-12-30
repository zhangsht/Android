package com.study.android.zhangsht.datasave;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int visibility;
    private EditText newPassword;
    private  EditText conPassword;
    private TextInputLayout newPassworddText;
    private TextInputLayout conPasswordText;
    private Button okButtton;
    private  Button clearButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newPassword = (EditText)findViewById(R.id.newPassword);
        conPassword = (EditText)findViewById(R.id.conPassword);
        newPassworddText = (TextInputLayout)findViewById(R.id.newPasswordText);
        conPasswordText = (TextInputLayout)findViewById(R.id.conPasswordText);
        okButtton = (Button)findViewById(R.id.okButton);
        clearButton = (Button)findViewById(R.id.clearButton);
        conPassword.setHint("Confirm Password");
        visibility = 1;
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        visibility = sharedPreferences.getInt("visibility", 1);
        if (1 != visibility) {
            conPasswordText.setErrorEnabled(false);
            conPassword.setHint("Password");
            newPassworddText.setVisibility(View.INVISIBLE);
            newPassword.setVisibility(View.INVISIBLE);
        }

        okButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility == 1) {
                    if (newPassword.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    } else if (conPassword.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "confirm your password", Toast.LENGTH_SHORT).show();
                    } else if (!conPassword.getText().toString().equals(newPassword.getText().toString())) {
                        conPassword.setText("");
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("visibility", 0);
                        editor.putString("password", conPassword.getText().toString());
                        editor.commit();

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, DataActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    if (!sharedPreferences.getString("password", "default").equals(conPassword.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, DataActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conPassword.setText("");
                newPassword.setText("");
            }
        });
    }
}
