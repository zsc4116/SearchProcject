package com.ikeeko.searchproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikeeko.searchproject.R;
import com.ikeeko.searchproject.bean.HomeArticleBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ZSC on 2020-06-23.
 */
public class HomeContentAdapter extends RecyclerView.Adapter<HomeContentAdapter.VH> {

    private List<HomeArticleBean> mData;

    public void setData(@NonNull List<HomeArticleBean> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        int size = data.size();
        notifyItemChanged(0, size);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_content, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvDesc.setText(mData.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvDesc;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_home_content_title);
            tvDesc = itemView.findViewById(R.id.tv_item_home_content_desc);
        }
    }
}
