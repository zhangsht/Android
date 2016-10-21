package com.study.android.zhangsht.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangsht on 2016/10/17.
 */
class Contactor {
    private String name;
    private String phoneNumber;
    private String type;
    private String ownerArea;
    private String background;

    public Contactor(String name, String phoneNumber, String type, String ownerArea, String background) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.ownerArea = ownerArea;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public String getOwnerArea() {
        return ownerArea;
    }

    public String getBackground() {
        return background;
    }
}


public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Contactor> list;

    public MyAdapter(Context context, List<Contactor> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder viewHolder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(list.get(i).getName().substring(0, 1));
        viewHolder.name.setText(list.get(i).getName());
        return convertView;
    }


    private class ViewHolder {
        public TextView title;
        public TextView name;
    }
}
