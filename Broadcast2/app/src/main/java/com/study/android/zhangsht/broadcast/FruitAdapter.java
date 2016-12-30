package com.study.android.zhangsht.broadcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhangsht on 2016/10/21.
 */



public class FruitAdapter extends BaseAdapter {
    private Context context;
    private List<Fruit> list;

    public FruitAdapter(Context context, List<Fruit> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
            viewHolder.kind = (TextView) convertView.findViewById(R.id.kind);
            convertView.setTag(viewHolder);
        } else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.logo.setImageResource(list.get(i).getLogo());
        viewHolder.kind.setText(list.get(i).getKind());
        return convertView;
    }


    private class ViewHolder {
        public ImageView logo;
        public TextView kind;
    }
}

class Fruit {
    private int logo;
    private String kind;

    public Fruit(int logo, String kind) {
        this.logo = logo;
        this.kind = kind;
    }

    public int getLogo() {
        return logo;
    }

    public String getKind() {
        return kind;
    }
}
