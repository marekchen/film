package com.droi.film.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.fragment.CommentEditFragment;
import com.droi.film.fragment.CommentListFragment;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.sdk.analytics.DroiAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2016/12/28.
 */

public class CommentListActivity extends FragmentActivity implements OnFragmentInteractionListener {

    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.top_bar_back_btn)
    ImageButton backArrowButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        topBarTitle.setText(getString(R.string.fragment_mine_comment));
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment btFragment;
        btFragment = CommentListFragment.newInstance();
        transaction.replace(R.id.id_content, btFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(int action) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        DroiAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DroiAnalytics.onPause(this);
    }
}
