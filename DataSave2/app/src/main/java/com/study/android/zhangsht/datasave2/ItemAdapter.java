package com.study.android.zhangsht.datasave2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangsht on 2016/11/20.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Info> list;


    public ItemAdapter(Context context, List<Info> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.birthDay = (TextView) convertView.findViewById(R.id.birthDay);
            viewHolder.gift = (TextView)convertView.findViewById(R.id.gift);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).getName());
        viewHolder.birthDay.setText(list.get(position).getBirthDate());
        viewHolder.gift.setText(list.get(position).getGift());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView birthDay;
        public TextView gift;
    }
}

class Info {
    private String name;
    private String birthDate;
    private String gift;

    public Info() {}

    public Info(String name, String birthDate, String gift) {
        this.name = name;
        this.birthDate = birthDate;
        this.gift = gift;
    }

    public String getName() {
        return name;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getGift() {
        return gift;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
