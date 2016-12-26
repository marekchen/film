package com.droi.film.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.film.R;
import com.droi.film.model.CastBean;

import java.util.List;

/**
 * Created by marek on 2016/12/26.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastItemHolder> {

    private Context mContext;
    private List<CastBean> mDataList;
    private LayoutInflater mLayoutInflater;

    public CastAdapter(Context mContext, List<CastBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CastAdapter.CastItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastItemHolder(mLayoutInflater.inflate(R.layout.view_item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(CastItemHolder holder, int position) {
        CastBean cast = mDataList.get(position);
        holder.nameTextView.setText(cast.getName());
        Glide.with(mContext).load(cast.getAvatarUrl()).into(holder.avatarImageView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class CastItemHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView avatarImageView;

        public CastItemHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_item_cast_name);
            avatarImageView = (ImageView) itemView.findViewById(R.id.iv_item_cast_avatar);
            itemView.findViewById(R.id.base_swipe_item_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showNewsDetail(getPosition());
                }
            });
        }
    }
}
