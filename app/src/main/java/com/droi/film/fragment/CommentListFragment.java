package com.droi.film.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droi.film.R;
import com.droi.film.adapter.CommentAdapter;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiang on 16/12/26.
 */

public class CommentListFragment extends Fragment {
    private static final String FILM_PARAM = "film";

    private FilmBean filmBean;
    CommentAdapter mAdapter;
    private int offset = 0;
    boolean isRefreshing = false;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.rc_list_comment)
    RecyclerView commentRecyclerView;
    @BindView(R.id.fragment_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public CommentListFragment() {
    }

    public static CommentListFragment newInstance(FilmBean filmBean) {
        CommentListFragment fragment = new CommentListFragment();
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
        View view = inflater.inflate(R.layout.fragment_list_comment, container, false);
        ButterKnife.bind(this, view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };*/
        List<Comment> list = new ArrayList<>();
        mAdapter = new CommentAdapter(getActivity(), list);
        commentRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                fetchComment();
            }
        });
        return view;
    }

    public void onButtonPressed(int action) {
        if (mListener != null) {
            mListener.onFragmentInteraction(action);
        }
    }

    private void fetchComment() {
        if (offset == 0) {
            setRefreshing(true);
        }
        if (isRefreshing) {
            return;
        }
        DroiCondition cond = DroiCondition.cond("refId", DroiCondition.Type.EQ, filmBean.getObjectId());
        DroiQuery query = DroiQuery.Builder.newBuilder().offset(offset).limit(10).where(cond).query(Comment.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Comment>() {
            @Override
            public void result(List<Comment> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        if (offset == 0) {
                            mAdapter.clear();
                        }
                        mAdapter.append(list);
                        offset = mAdapter.getItemCount();
                    }
                } else {
                    //做请求失败处理
                }
                setRefreshing(false);
                isRefreshing = false;
            }
        });
    }

    void setRefreshing(final boolean b) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(b);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchComment();
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
}
