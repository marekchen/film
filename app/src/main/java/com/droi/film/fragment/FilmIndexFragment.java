package com.droi.film.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.film.R;
import com.droi.film.activity.CommentEditActivity;
import com.droi.film.adapter.CastAdapter;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.CastBean;
import com.droi.film.model.FilmBean;
import com.droi.sdk.core.DroiReferenceObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilmIndexFragment extends Fragment {

    private static final String FILM_PARAM = "film";

    private FilmBean filmBean;
    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.iv_movie)
    ImageView movieImageView;
    @BindView(R.id.tv_rating_star)
    TextView ratingStarTextView;
    @BindView(R.id.tv_rating_count)
    TextView ratingCountTextView;
    @BindView(R.id.tv_release_time)
    TextView releaseTextView;
    @BindView(R.id.tv_genres)
    TextView genresTextView;
    @BindView(R.id.tv_countries)
    TextView countriesTextView;
    @BindView(R.id.tv_summary)
    TextView summaryTextView;
    @BindView(R.id.cast_recycle_view)
    RecyclerView castRecycleView;
    @BindView(R.id.comment_recycle_view)
    RecyclerView commentRecycleView;

    public FilmIndexFragment() {
    }

    public static FilmIndexFragment newInstance(FilmBean filmBean) {
        FilmIndexFragment fragment = new FilmIndexFragment();
        Bundle args = new Bundle();
        args.putParcelable(FILM_PARAM, filmBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filmBean = getArguments().getParcelable(FILM_PARAM);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_index, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (filmBean != null) {
            Glide.with(this)
                    .load(filmBean.getImage()).into(movieImageView);
            ratingStarTextView.setText("" + filmBean.getStar());
            ratingCountTextView.setText("" + filmBean.getCommentsCount());
            releaseTextView.setText(filmBean.getReleaseTime());
            String genresString = "";
            ArrayList genres = filmBean.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                genresString += genres.get(i) + (i != genres.size() - 1 ? " / " : "");
            }
            genresTextView.setText(genresString);

            String countriesString = "";
            ArrayList countries = filmBean.getCountries();
            for (int i = 0; i < countries.size(); i++) {
                countriesString += countries.get(i) + (i != countries.size() - 1 ? " / " : "");
            }
            countriesTextView.setText(countriesString);
            summaryTextView.setText(filmBean.getSummary());
            //summaryTextView.setText("dddddddddddddddddddddddddddddddddd");
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            castRecycleView.setLayoutManager(mLayoutManager); //绑上列表管理器
            CastBean castBean = new CastBean();
            castBean.setName("梁朝伟");
            castBean.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/33525.jpg");
            CastBean castBean2 = new CastBean();
            castBean2.setName("金城武");
            castBean2.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/6925.jpg");
            CastBean castBean3 = new CastBean();
            castBean3.setName("陈奕迅");
            castBean3.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/20210.jpg");
            CastBean castBean4 = new CastBean();
            castBean4.setName("杨颖");
            castBean4.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/50831.jpg");
/*            ArrayList<DroiReferenceObject> casts = new ArrayList<>();
            DroiReferenceObject ref1 = new DroiReferenceObject();
            ref1.setDroiObject(castBean);
            DroiReferenceObject ref2 = new DroiReferenceObject();
            ref2.setDroiObject(castBean2);
            DroiReferenceObject ref3 = new DroiReferenceObject();
            ref3.setDroiObject(castBean3);
            DroiReferenceObject ref4 = new DroiReferenceObject();
            ref4.setDroiObject(castBean4);
            casts.add(ref1);
            casts.add(ref2);
            casts.add(ref3);
            casts.add(ref4);*/
            /*ArrayList<CastBean> casts = new ArrayList<>();
            casts.add(castBean);
            casts.add(castBean2);
            casts.add(castBean3);
            casts.add(castBean4);*/
            ArrayList<CastBean> casts = convert(filmBean.getCasts());
            Log.i("chenpei", "123");
            RecyclerView.Adapter mAdapter = new CastAdapter(getActivity(), casts);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            castRecycleView.setAdapter(mAdapter);
            castRecycleView.setLayoutManager(layoutManager);
        }
        return view;
    }

    public ArrayList<CastBean> convert(ArrayList<DroiReferenceObject> refs) {
        ArrayList<CastBean> casts = new ArrayList<CastBean>();
        if (refs != null) {
            for (DroiReferenceObject ref : refs) {
                casts.add((CastBean) ref.droiObject());
            }
        }
        return casts;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int action) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.comment1)
    void comment1() {
        Intent intent = new Intent(getActivity(), CommentEditActivity.class);
        intent.putExtra("Film", filmBean);
        startActivity(intent);
    }

}
