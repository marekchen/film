package com.droi.film.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.model.Comment;

import java.util.List;

/**
 * Created by marek on 2016/12/26.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CastItemHolder> {

    private Context mContext;
    private List<Comment> mDataList;
    private LayoutInflater mLayoutInflater;

    public CommentAdapter(Context mContext, List<Comment> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void clear() {
        mDataList.clear();
    }

    public void append(Comment comment) {
        if (null == comment) {
            return;
        }
        mDataList.add(comment);
    }

    public void append(List<Comment> comments) {
        if (comments == null) {
            return;
        }
        mDataList.addAll(comments);
        notifyDataSetChanged();
    }


    @Override
    public CommentAdapter.CastItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastItemHolder(mLayoutInflater.inflate(R.layout.view_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CastItemHolder holder, int position) {
        Comment comment = mDataList.get(position);
        holder.nameTextView.setText(comment.commenter.getUserId());
        holder.ratingBar.setRating(comment.star);
        holder.contentTextView.setText(comment.comment);
        String sCommentFormat = mContext.getResources().getString(R.string.label_comment_useful);
        String sFinalComment = String.format(sCommentFormat, comment.usefullNum);
        holder.usefulCount.setText(sFinalComment);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class CastItemHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        RatingBar ratingBar;
        TextView contentTextView;
        TextView usefulCount;

        public CastItemHolder(View itemView) {
            super(itemView);
            usefulCount = (TextView) itemView.findViewById(R.id.tv_usefulCount);
            contentTextView = (TextView) itemView.findViewById(R.id.tv_review_content);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_review_author);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rb_review_rating);
        }
    }
}
