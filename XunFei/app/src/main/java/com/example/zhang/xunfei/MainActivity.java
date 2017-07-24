package com.example.zhang.xunfei;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
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
    private boolean isReady = false;
    int hor = 20, ver = 20;

    private final String[] command = {"开启声控", "向前", "向后", "向左", "向右", "放大", "缩小"};

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
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
                //Toast.makeText(context, "第 " + count + " 次" , Toast.LENGTH_SHORT).show();
                count++;
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    private void printResult(RecognizerResult results) {
        String text = parseJsonVoice(results.getResultString());
        content.append(text);
        checkCommand(text);
    }

    private void checkCommand(String text) {
        for (int i =0; i < command.length; i++) {
            String regEx = command[i];
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(text);
            if (isReady && matcher.find()) {
                followCommand(regEx);
            } else if (i == 0 && matcher.find()) {
                isReady = true;
            }
        }
    }

    private void followCommand(String regEx) {
        if (regEx.equals("向后")) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, ver, 0, 0);
            ver += 20;
            showCommand.setLayoutParams(lp);
        } else if (regEx.equals("向右")) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor += 20;
            showCommand.setLayoutParams(lp);
        } else if (regEx.equals("放大")) {
            showCommand.setImageResource(R.mipmap.ic_launcher2);
        } else if (regEx.equals("向左")) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor -= 20;
            showCommand.setLayoutParams(lp);
        } else if (regEx.equals("向前")) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(hor, 0, 0, 0);
            hor -= 20;
            showCommand.setLayoutParams(lp);
        } else if (regEx.equals("缩小")) {
            showCommand.setImageResource(R.mipmap.ic_launcher);
        }
    }


    /**
     * 解析语音json
     */
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
