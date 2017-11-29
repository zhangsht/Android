package com.example.zhang.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhang.integration.R;
import com.example.zhang.integration.TimeRulerActivity;
import com.example.zhang.utils.Constans;

/**
 * Created by zhang on 2017/11/9.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private TextView tv_name;
    private String TAG = "HomeFragment";
    public static HomeFragment newInstance(String name) {
        HomeFragment _instance = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.ARGS, name);
        _instance.setArguments(bundle);
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv_name = view.findViewById(R.id.fg_home_tvName);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString(Constans.ARGS);
            if (!TextUtils.isEmpty(name)) {
                tv_name.setText(name);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
