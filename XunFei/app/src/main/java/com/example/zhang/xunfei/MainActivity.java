package com.example.zhang.xftest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText content;
    ImageView showCommand;
    int count = 0;
    private boolean isGot = false;
    int hor = 20, ver = 20;
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;
    private final int BIGGER = 4;
    private final int SMALLER = 5;


    private final String[] commands = {"上", "下", "左", "右", "放大", "缩小"};
    private final Pattern patterns[] = {Pattern.compile("上"), Pattern.compile("下"),
            Pattern.compile("左"), Pattern.compile("右"), Pattern.compile("放大"), Pattern.compile("缩小")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.starter).setOnClickListener(this);
        content = (EditText)findViewById(R.id.content);
        showCommand = (ImageView)findViewById(R.id.showCommand);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=597047e2");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.starter:
                startRecognizer(this);
                break;
            default:
                break;
        }

    }

    private void startRecognizer(final Context context) {
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.DOMAIN, "iat");
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        dialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
        dialog.setParameter(SpeechConstant.VAD_BOS, "4000");
        dialog.setParameter(SpeechConstant.VAD_EOS, "1000");
        dialog.setParameter(SpeechConstant.ASR_PTT, "0");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
                count++;
            }
            @Override
            public void onError(SpeechError speechError) {
                Toast.makeText(context, speechError.getPlainDescription(true), Toast.LENGTH_SHORT).show();
            }
        });
        isGot = false;
        dialog.show();
        Toast.makeText(this, "等待语音指令中...", Toast.LENGTH_SHORT).show();
    }

    private void printResult(RecognizerResult results) {
        String text = parseJsonVoice(results.getResultString());
        checkCommand(text);
    }

    private void checkCommand(String text) {
        int result = -1;
        int index = -1;
        for (int i = 0; i < 6; i++) {
            Matcher matcher = patterns[i].matcher(text);
            if (matcher.find()) {
                result++;
                index = i;
            }
        }

        if (result == 0) {
            followCommand(index);
            content.setText("检测到：\"" + commands[index] + "\" 指令");
            if (index < 4) {
                feedbackInfo("将向" + commands[index] + "旋转");
            } else {
                feedbackInfo("视野将" + commands[index]);
            }
            isGot = true;
        } else if (result > 0) {
            feedbackInfo("检测到多个指令冲突");
            isGot = true;
        } else if (!isGot) {
            feedbackInfo("指令不正确");
        }
    }

    private void feedbackInfo(String info) {
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(MainActivity.this, null);

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        mTts.startSpeaking(info, null);
    }

    private void followCommand(int index) {
        if (index == DOWN) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, ver, 0, 0);
            ver += 40;
            showCommand.setLayoutParams(lp);
        } else if (index == RIGHT) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor += 40;
            showCommand.setLayoutParams(lp);
        } else if (index == BIGGER) {
            showCommand.setImageResource(R.mipmap.ic_launcher2);
        } else if (index == LEFT) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor -= 40;
            showCommand.setLayoutParams(lp);
        } else if (index == UP) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor -= 40;
            showCommand.setLayoutParams(lp);
        } else if (index == SMALLER) {
            showCommand.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public String parseJsonVoice(String resultString) {

        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(resultString);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
