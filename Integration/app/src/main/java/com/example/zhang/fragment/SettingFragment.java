package com.example.zhang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhang.activityJump.PageJump;
import com.example.zhang.integration.QRCodeActivity;
import com.example.zhang.integration.R;
import com.example.zhang.integration.TimeRulerActivity;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by zhang on 2017/11/29.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SettingFragment";
    private Button mbtnTimeRuller, mbtnGenerateQRCode, mbtnJumpForResult;

    public static SettingFragment newInstance() {
        SettingFragment _instance = new SettingFragment();
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mbtnTimeRuller = view.findViewById(R.id.btn_time_ruler);
        mbtnTimeRuller.setOnClickListener(this);
        mbtnGenerateQRCode = view.findViewById(R.id.btn_generate_qr_code);
        mbtnGenerateQRCode.setOnClickListener(this);
        mbtnJumpForResult = view.findViewById(R.id.btn_jump_for_result);
        mbtnJumpForResult.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_time_ruler:
                intent.setClass(getActivity(), TimeRulerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_generate_qr_code:
                intent.setClass(getActivity(), QRCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_jump_for_result:
                intent.setClass(getActivity(), PageJump.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult");
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == RESULT_CANCELED) {
                String str = data.getExtras().getString("msg");
                Log.e(TAG, str);
            }
        }
    }
}
