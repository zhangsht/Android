package com.study.android.zhangsht.music;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static com.study.android.zhangsht.music.MusicServervice.mp;

public class MainActivity extends AppCompatActivity {
    private Handler mhandler;
    private Runnable mRunnable;
    private MusicServervice ms;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ms = ((MusicServervice.MyBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            ms = null;
        }
    };

    private SimpleDateFormat time;

    private Button playAndPause;
    private Button stop;
    private Button quit;
    private SeekBar seekBar;
    private TextView playedTime;
    private TextView status;
    private TextView totalTime;
    private ImageView imageView;
    private int roate;

    private void bindServiceConnection() {
        Intent intent = new Intent(this, MusicServervice.class);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    private void init() {
        roate = 0;
        time = new SimpleDateFormat("mm:ss");
        playAndPause = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);
        quit = (Button)findViewById(R.id.quit);
        status = (TextView) findViewById(R.id.status);
        totalTime = (TextView) findViewById(R.id.totalTime);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        playedTime = (TextView)findViewById(R.id.palyedTime);
        imageView = (ImageView)findViewById(R.id.musicImage);

        seekBar.setProgress(mp.getCurrentPosition());
        seekBar.setMax(mp.getDuration());

        mhandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                playedTime.setText(time.format(mp.getCurrentPosition()));
                totalTime.setText(time.format(mp.getDuration()));
                seekBar.setProgress(mp.getCurrentPosition());
                mhandler.postDelayed(mRunnable, 100);
               if (mp.isPlaying()) {
                   imageView.setRotation((++roate));
                   roate %= 360;
               }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindServiceConnection();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if (fromUser && mp != null) {
                   mp.seekTo(progress);
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });
        playAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playAndPause.getText().toString().equals("播放")) {
                    ms.play();
                    playAndPause.setText("暂停");
                    status.setText("播放");
                } else {
                    ms.pause();
                    playAndPause.setText("播放");
                    status.setText("暂停");
                }
                playedTime.setText(time.format(mp.getDuration()));
                mhandler.post(mRunnable);
                seekBar.setMax(mp.getDuration());
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.stop();
                playAndPause.setText("播放");
                status.setText("停止");
                roate = 0;
                imageView.setRotation(roate);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mhandler.removeCallbacks(mRunnable);
                unbindService(sc);
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
