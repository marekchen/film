package com.droi.film.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.droi.film.R;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiang on 16/12/26.
 */

public class CommentEditFragment extends Fragment {

    private static final String FILM_PARAM = "film";
    private FilmBean filmBean;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @BindView(R.id.rb_review_rating)
    RatingBar mRatingBar;

    @BindView(R.id.et_reviewSummary)
    EditText mReviewSummary;

    public CommentEditFragment() {
    }

    public static CommentEditFragment newInstance(FilmBean filmBean) {
        CommentEditFragment fragment = new CommentEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_comment_edit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onButtonPressed(int uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        if (mReviewSummary.getText().toString().length() < 1) {
            Toast.makeText(getActivity(), "评论不能为空", Toast.LENGTH_SHORT).show();
        } else {
            FilmUser user = FilmUser.getCurrentUser(FilmUser.class);
            Comment comment = new Comment(filmBean.getObjectId(), mReviewSummary.getText().toString().trim(), user, (int) mRatingBar.getRating());
            comment.save();
            getFragmentManager().popBackStack();
        }
    }

}
