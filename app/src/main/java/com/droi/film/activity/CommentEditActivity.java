package com.droi.film.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.droi.film.R;
import com.droi.film.fragment.CommentEditFragment;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.FilmBean;

/**
 * Created by marek on 2016/12/28.
 */

public class CommentEditActivity extends FragmentActivity implements OnFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);
        FilmBean filmBean = getIntent().getExtras().getParcelable("Film");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment btFragment = CommentEditFragment.newInstance(filmBean);
        transaction.replace(R.id.id_content, btFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(int action) {

    }
}
