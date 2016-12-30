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

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {
    private ArrayList<Index> indexList;
    private LayoutInflater mInflater;

    public void clear() {
        indexList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position, Index item);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public IndexAdapter(Context context, ArrayList<Index> items) {
        super();
        indexList = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IndexAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.index_item, viewGroup, false);
        IndexAdapter.ViewHolder holder = new ViewHolder(view);
        holder.Title = (TextView)view.findViewById(R.id.indexTitle);
        holder.Content = (TextView)view.findViewById(R.id.indexCon);
        return holder;
    }

    @Override
    public void onBindViewHolder(final IndexAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.Title.setText(indexList.get(i).getTitle());
        viewHolder.Content.setText(indexList.get(i).getContent());

        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i, indexList.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return indexList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        TextView Title;
        TextView Content;
    }
}

class Index {
    private String title;
    private String content;

    public Index(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setContent(String content) {
        this.content = content;
    }
}