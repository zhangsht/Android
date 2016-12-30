package com.study.android.zhangsht.datasave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangsht on 2016/11/9.
 */

public class DataActivity extends AppCompatActivity {
    private Button save;
    private Button load;
    private Button clear;
    private EditText data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);

        save = (Button)findViewById(R.id.saveButton);
        load = (Button)findViewById(R.id.loadButton);
        clear = (Button)findViewById(R.id.clearButton);
        data = (EditText)findViewById(R.id.data);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput("Data", MODE_PRIVATE)) {
                    fileOutputStream.write(data.getText().toString().getBytes());
                    Toast.makeText(DataActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(DataActivity.this, "Fail to save file", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput("Data")) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    data.setText(new String(contents));
                    Toast.makeText(DataActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(DataActivity.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to load file");
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setText("");
            }
        });


    }
}
