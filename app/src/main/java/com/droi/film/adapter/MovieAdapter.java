package com.droi.film.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.film.R;
import com.droi.film.activity.FilmDetailActivity;
import com.droi.film.model.Banner;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.view.Indicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2016/12/26.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private List<FilmBean> dataList;
    private ArrayList<Banner> banners = new ArrayList<>();
    private Context mContext;

    public void append(List<FilmBean> filmBeens) {
        if (filmBeens == null) {
            return;
        }
        dataList.addAll(filmBeens);
        notifyDataSetChanged();
    }

    //构造函数
    public MovieAdapter(Context context, List<FilmBean> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        Banner banner = new Banner();
        banner.setImageUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/jrfbaj5pxo/1080x300.jpg");

        Banner banner2 = new Banner();
        banner2.setImageUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/5zshqvupyw/1080x300.png");

        Banner banner3 = new Banner();
        banner3.setImageUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/gjcdeo1up3/1080x300.png");

        Banner banner4 = new Banner();
        banner4.setImageUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/0xmc6y9hxe/1080x300.png");

        Banner banner5 = new Banner();
        banner5.setImageUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/kosftkb7nv/1080x300.png");
        this.banners.add(banner);
        this.banners.add(banner2);
        this.banners.add(banner3);
        this.banners.add(banner4);
        this.banners.add(banner5);
    }

    /**
     * 判断当前position是否处于第一个
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_banner_main, null));
        } else {
            return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item_movie, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (isHeader(position)) {
            //((HeaderViewHolder) viewHolder).getTextView().setText("This is the Header!!");
            final Indicator indicatorLayout = ((HeaderViewHolder) viewHolder).indicatorLayout;
            ((HeaderViewHolder) viewHolder).indicatorLayout.setCount(banners.size());
            ((HeaderViewHolder) viewHolder).indicatorLayout.select(0);
            ViewPager viewPager;
            viewPager = ((HeaderViewHolder) viewHolder).viewPager;
            BannerAdapter mBannerAdapter = new BannerAdapter(mContext, banners);
            viewPager.setAdapter(mBannerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    indicatorLayout.select(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            final GestureDetector tapGestureDetector = new GestureDetector(mContext, new TapGestureListener());
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    tapGestureDetector.onTouchEvent(event);
                    return false;
                }
            });

        } else {
            ((BodyViewHolder) viewHolder).textView.setText(dataList.get(position - 1).getTitle());
            //Log.i("chenpei", "》》" + ((CastBean) dataList.get(position - 1).getCasts().get(0).droiObject()).getName());
            ImageView imageView = ((BodyViewHolder) viewHolder).imageView;
            Glide.with(mContext).load(dataList.get(position - 1).getImage()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, FilmDetailActivity.class);
                    intent.putExtra("Film", dataList.get(position - 1));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    /**
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果是0，就是头，否则则是其他的item
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_view_pager)
        ViewPager viewPager;
        @BindView(R.id.head_indicator_layout)
        Indicator indicatorLayout;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public class BodyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv)
        TextView textView;
        @BindView(R.id.item_iv)
        ImageView imageView;

        public BodyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }
    }


/*
    private Context mContext;
    private List<CastBean> mDataList;
    private LayoutInflater mLayoutInflater;

    public MovieAdapter(Context mContext, List<CastBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MovieAdapter.CastItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    }*/
}
