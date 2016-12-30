package com.droi.film.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.droi.film.R;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jiang on 16/12/26.
 */

public class CommentEditFragment extends Fragment {

    public static final String FILM_PARAM = "film";
    public static final String COMMENT_PARAM = "comment";
    public static final String TYPE_PARAM = "type";

    private Unbinder unbinder;
    private FilmBean filmBean;
    private Comment comment;
    private int type = 0;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.rb_review_rating)
    RatingBar mRatingBar;
    @BindView(R.id.v_header)
    RelativeLayout mRatingLayout;
    @BindView(R.id.et_reviewSummary)
    EditText mReviewSummary;
    @BindView(R.id.top_bar_delete_btn)
    ImageButton deleteButton;

    public CommentEditFragment() {
    }

    public static CommentEditFragment newInstance(FilmBean filmBean, int type, Comment comment) {
        CommentEditFragment fragment = new CommentEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(FILM_PARAM, filmBean);
        args.putParcelable(COMMENT_PARAM, comment);
        args.putInt(TYPE_PARAM, type);
        fragment.setArguments(args);
        return fragment;
    }

    public static CommentEditFragment newInstance(FilmBean filmBean, int type) {
        CommentEditFragment fragment = new CommentEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(FILM_PARAM, filmBean);
        args.putInt(TYPE_PARAM, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filmBean = getArguments().getParcelable(FILM_PARAM);
            comment = getArguments().getParcelable(COMMENT_PARAM);
            type = getArguments().getInt(TYPE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_edit, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        if (comment == null) {
            deleteButton.setVisibility(View.GONE);
            if (type == 1) {
                mRatingLayout.setVisibility(View.GONE);
            }
        } else {
            if (type == 2 || comment.type == 2) {
                mRatingBar.setMax(5);
                mRatingBar.setNumStars(5);
                mRatingBar.setRating(comment.star);
            } else {
                mRatingLayout.setVisibility(View.GONE);
            }
            mReviewSummary.setText(comment.comment);
        }
        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.top_bar_delete_btn)
    public void delete(View view) {
        comment.deleteInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean) {
                    if (aBoolean) {
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        onButtonPressed(1);
                    } else {
                        Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @OnClick(R.id.top_bar_back_btn)
    public void back() {
        onButtonPressed(2);
    }

    @OnClick(R.id.top_bar_send_btn)
    public void submit(View view) {
        if (mReviewSummary.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "评论不能为空", Toast.LENGTH_SHORT).show();
        } else {
            FilmUser user = FilmUser.getCurrentUser(FilmUser.class);

            if (comment == null) {
                Comment comment;
                if (type == 1) {
                    comment = new Comment(filmBean.getObjectId(), mReviewSummary.getText().toString().trim(), user);
                } else {
                    comment = new Comment(filmBean.getObjectId(), mReviewSummary.getText().toString().trim(), user, (int) mRatingBar.getRating());
                }
                comment.saveInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        if (aBoolean) {
                            Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
                            onButtonPressed(1);
                        } else {
                            Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                if (type == 2 || comment.type == 2) {
                    comment.star = (int) mRatingBar.getRating();
                    comment.type = 2;
                }
                comment.comment = mReviewSummary.getText().toString().trim();
                comment.saveInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        if (aBoolean) {
                            Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
                            onButtonPressed(1);
                        } else {
                            Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "CommentEditFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "CommentEditFragment");
    }

}
