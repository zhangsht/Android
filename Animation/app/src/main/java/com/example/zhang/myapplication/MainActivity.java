package com.example.zhang.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }
//    static final String TAG = "zhangsht";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.start);
//        Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
//        button.startAnimation(translateAnimation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native void stringFromJNI();

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
//            t.start();
        }
    }

//    Thread t = new Thread() {
//        @Override
//        public void run() {
//            stringFromJNI();
//        }
//    };
}
