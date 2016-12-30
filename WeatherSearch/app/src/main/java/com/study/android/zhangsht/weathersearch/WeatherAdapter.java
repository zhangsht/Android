package com.study.android.zhangsht.weathersearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zhangsht on 2016/11/25.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private ArrayList<Weather> weatherList;
    private LayoutInflater mInflater;

    public void clear() {
        weatherList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position, Weather item);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public WeatherAdapter(Context context, ArrayList<Weather> items) {
        super();
        weatherList = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.weather_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.Date = (TextView)view.findViewById(R.id.date);
        holder.WeatherDes = (TextView)view.findViewById(R.id.weatherDes);
        holder.Temperature = (TextView)view.findViewById(R.id.temperature);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.Date.setText(weatherList.get(i).getDate());
        viewHolder.WeatherDes.setText(weatherList.get(i).getWeatherDes());
        viewHolder.Temperature.setText(weatherList.get(i).getTemperature());

        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i, weatherList.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        TextView Date;
        TextView WeatherDes;
        TextView Temperature;
    }
}


class Weather {
    private String date;
    private String weatherDes;
    private String temperature;

    public Weather(String date, String weatherDes, String temperature) {
        this.date = date;
        this.weatherDes = weatherDes;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public String getWeatherDes() {
        return weatherDes;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeatherDes(String weatherDes) {
        this.weatherDes = weatherDes;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
