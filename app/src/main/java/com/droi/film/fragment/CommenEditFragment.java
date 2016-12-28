package com.droi.film.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jiang on 16/12/26.
 */

public class CommenEditFragment extends Fragment {

    private static final String FILM_PARAM = "film";
    private FilmBean filmBean;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    @BindView(R.id.rb_review_rating)
    RatingBar mRatingBar;

    @BindView(R.id.et_reviewSummary)
    EditText mReviewSummary;

    public CommenEditFragment() {
    }

    public static CommenEditFragment newInstance(FilmBean filmBean) {
        CommenEditFragment fragment = new CommenEditFragment();
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
        return inflater.inflate(R.layout.fragment_subject_collection, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        if (mReviewSummary.getText().toString().length() < 1){
            Toast.makeText(getActivity(),"评论不能为空",Toast.LENGTH_SHORT).show();
        }else{
            FilmUser user = FilmUser.getCurrentUser(FilmUser.class);
            Comment comment = new Comment(filmBean.getObjectId(),mReviewSummary.getText().toString().trim(),user);
            comment.save();
            getFragmentManager().popBackStack();
        }
    }

}
