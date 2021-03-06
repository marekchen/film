package com.droi.film.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.droi.film.R;
import com.droi.film.fragment.CommentEditFragment;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.sdk.analytics.DroiAnalytics;

/**
 * Created by marek on 2016/12/28.
 */

public class CommentEditActivity extends FragmentActivity implements OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);
        FilmBean filmBean = getIntent().getExtras().getParcelable(CommentEditFragment.FILM_PARAM);
        Comment comment = getIntent().getExtras().getParcelable(CommentEditFragment.COMMENT_PARAM);
        int type = getIntent().getExtras().getInt(CommentEditFragment.TYPE_PARAM);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment btFragment;
        btFragment = CommentEditFragment.newInstance(filmBean, type, comment);
        transaction.replace(R.id.id_content, btFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(int action) {
        Intent intent = new Intent();
        if (action == 1) {
            intent.putExtra("fresh", true);
        }
        setResult(RESULT_OK, intent);
        finish();
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
