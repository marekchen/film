package com.droi.film.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droi.film.R;
import com.droi.film.adapter.MovieAdapter;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.FilmBean;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FilmFragment extends Fragment {
    private static final String ARG_PARAM1 = "type";

    private int type;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    MovieAdapter movieAdapter;

    @BindView(R.id.movie_rv)
    RecyclerView movieGridView;

    public FilmFragment() {
    }

    public static FilmFragment newInstance(int type) {
        FilmFragment fragment = new FilmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showing, container, false);
        unbinder = ButterKnife.bind(this, view);
        movieGridView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<FilmBean> list = new ArrayList<>();
        movieAdapter = new MovieAdapter(getActivity(), list);
        movieGridView.setAdapter(movieAdapter);

        DroiCondition cond = DroiCondition.cond("type", DroiCondition.Type.EQ, type);
        DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).query(FilmBean.class).build();
        query.runQueryInBackground(new DroiQueryCallback<FilmBean>() {
            @Override
            public void result(List<FilmBean> list, DroiError droiError) {
                Log.i("chenpei", "query:" + droiError.toString());
                if (droiError.isOk() && list.size() > 0) {
                    movieAdapter.append(list);
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });


        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return movieAdapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });

        movieGridView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.i("chenpei", "fd" + type);
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
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "FilmFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "FilmFragment");
    }
}
