package com.example.zhang.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhang.integration.R;

import java.util.ArrayList;

/**
 * Created by zhang on 2017/12/1.
 */

public class SettingButtonAdapter extends BaseAdapter {
    private ArrayList<Integer> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public SettingButtonAdapter(Context context, ArrayList<Integer> settingButtonData) {
        mContext = context;
        mData = settingButtonData;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButtonHolder buttonHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_items_setting_button, null);
            buttonHolder = new ButtonHolder(convertView);
            buttonHolder.button.setText(mContext.getResources().getString(mData.get(position)));
            convertView.setTag(buttonHolder);
        } else {
            buttonHolder = (ButtonHolder)convertView.getTag();
            buttonHolder.button.setText(mContext.getResources().getString(mData.get(position)));
        }
        return convertView;
    }

    class ButtonHolder {
        TextView button;
        ButtonHolder(View view) {
            button = view.findViewById(R.id.btn_setting_item);
        }
    }

}
