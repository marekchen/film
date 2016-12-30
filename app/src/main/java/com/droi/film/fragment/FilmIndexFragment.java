package com.droi.film.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.film.R;
import com.droi.film.activity.CommentEditActivity;
import com.droi.film.adapter.CastAdapter;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.CastBean;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.core.DroiUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

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
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_my_comment)
    TextView myCommentTextView;
    @BindView(R.id.comment1)
    Button button1;
    @BindView(R.id.comment2)
    Button button2;
    @BindView(R.id.cv_my_comment)
    CardView myCommentCardView;

    CastAdapter mCastAdapter;

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
        View view = inflater.inflate(R.layout.fragment_film_index, container, false);
        unbinder = ButterKnife.bind(this, view);
        getComment();
        if (filmBean != null) {
            Glide.with(this)
                    .load(filmBean.getImage()).into(movieImageView);
            ratingStarTextView.setText(String.format("%.1f", filmBean.getStar() * 2));
            String sCommentFormat = this.getResources().getString(R.string.label_comment_count);
            String sFinalComment = String.format(sCommentFormat, filmBean.getCommentsCount());
            ratingCountTextView.setText(sFinalComment);
            releaseTextView.setText(filmBean.getReleaseTime());
            ratingBar.setMax(5);
            ratingBar.setNumStars(5);
            ratingBar.setRating(filmBean.getStar());
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
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            castRecycleView.setLayoutManager(mLayoutManager);
/*            CastBean castBean = new CastBean();
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
            castBean4.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/50831.jpg");*/
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
            mCastAdapter = new CastAdapter(getActivity(), casts);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            castRecycleView.setAdapter(mCastAdapter);
            castRecycleView.setLayoutManager(layoutManager);
        }
        return view;
    }

    void getComment() {
        DroiUser droiUser = DroiUser.getCurrentUser();
        if (droiUser == null || droiUser.isAnonymous()) {
            return;
        }
        DroiCondition cond1 = DroiCondition.cond("refId", DroiCondition.Type.EQ, filmBean.getObjectId());
        DroiCondition cond2 = DroiCondition.cond("commenterId", DroiCondition.Type.EQ, droiUser.getObjectId());
        DroiCondition cond = cond1.and(cond2);
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(1).where(cond).query(Comment.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Comment>() {
            @Override
            public void result(List<Comment> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        final Comment comment = list.get(0);
                        myCommentCardView.setVisibility(View.VISIBLE);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = comment.getModifiedTime();
                        String mTime = sdf.format(date);
                        String sCommentFormat;
                        if (comment.type == 1) {
                            sCommentFormat = getActivity().getResources().getString(R.string.label_comment_prefix1);
                        } else {
                            sCommentFormat = getActivity().getResources().getString(R.string.label_comment_prefix2);
                        }
                        String sFinalComment = String.format(sCommentFormat, mTime, comment.comment);
                        myCommentTextView.setText(sFinalComment);
                        myCommentTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CommentEditActivity.class);
                                intent.putExtra(CommentEditFragment.FILM_PARAM, filmBean);
                                intent.putExtra(CommentEditFragment.TYPE_PARAM, 0);
                                intent.putExtra(CommentEditFragment.COMMENT_PARAM, comment);
                                startActivityForResult(intent, 1);
                            }
                        });
                        if (comment.type == 1) {
                            button2.setVisibility(View.VISIBLE);
                            button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), CommentEditActivity.class);
                                    intent.putExtra(CommentEditFragment.FILM_PARAM, filmBean);
                                    intent.putExtra(CommentEditFragment.TYPE_PARAM, 2);
                                    intent.putExtra(CommentEditFragment.COMMENT_PARAM, comment);
                                    startActivityForResult(intent, 1);
                                }
                            });
                        }
                    } else {
                        button1.setVisibility(View.VISIBLE);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CommentEditActivity.class);
                                intent.putExtra(CommentEditFragment.FILM_PARAM, filmBean);
                                intent.putExtra(CommentEditFragment.TYPE_PARAM, 1);
                                startActivityForResult(intent, 1);
                            }
                        });
                        button2.setVisibility(View.VISIBLE);
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CommentEditActivity.class);
                                intent.putExtra(CommentEditFragment.FILM_PARAM, filmBean);
                                intent.putExtra(CommentEditFragment.TYPE_PARAM, 2);
                                startActivityForResult(intent, 1);
                            }
                        });
                    }
                }
            }
        });
    }

    ArrayList<CastBean> convert(ArrayList<DroiReferenceObject> refs) {
        ArrayList<CastBean> casts = new ArrayList<CastBean>();
        if (refs != null) {
            for (DroiReferenceObject ref : refs) {
                casts.add((CastBean) ref.droiObject());
            }
        }
        return casts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                boolean bFresh = data.getBooleanExtra("fresh", false);
                if (bFresh) {
                    myCommentCardView.setVisibility(View.GONE);
                    button1.setVisibility(View.GONE);
                    button2.setVisibility(View.GONE);
                    getComment();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "FilmIndexFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "FilmIndexFragment");
    }
}
