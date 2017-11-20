package com.example.zhang.integration;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zhang.utils.QRCode;

public class QRCodeActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        editText = findViewById(R.id.QRtext);
        imageView = findViewById(R.id.QRImage);
        button = findViewById(R.id.QRButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = QRCode.createQRCode(editText.getText().toString(), 500);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
