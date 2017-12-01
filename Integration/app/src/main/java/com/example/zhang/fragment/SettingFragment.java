package com.example.zhang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zhang.adpter.SettingButtonAdapter;
import com.example.zhang.integration.PageJumpActivity;
import com.example.zhang.integration.QRCodeActivity;
import com.example.zhang.integration.R;
import com.example.zhang.integration.TimeRulerActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by zhang on 2017/11/29.
 */

public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment";
    private ListView mlvSettingButtons;
    private SettingButtonAdapter mSettingButtonAdapter;
    private ArrayList<Integer> mSettingButtonData;

    public static SettingFragment newInstance() {
        SettingFragment _instance = new SettingFragment();
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return bindView(view);
    }

    private View bindView(View view) {
        mlvSettingButtons = view.findViewById(R.id.lv_setting_btns);
        putDataIntoListView();
        mSettingButtonAdapter = new SettingButtonAdapter(getContext(), mSettingButtonData);
        mlvSettingButtons.setAdapter(mSettingButtonAdapter);
        mlvSettingButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:
                        intent.setClass(getActivity(), TimeRulerActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(getActivity(), QRCodeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(getActivity(), PageJumpActivity.class);
                        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }

    private void putDataIntoListView() {
        mSettingButtonData = new ArrayList<>();
        mSettingButtonData.add(R.string.timeRuler);
        mSettingButtonData.add(R.string.generateQRCode);
        mSettingButtonData.add(R.string.jumpForResult);
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
