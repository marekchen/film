package com.droi.film.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.adapter.MyFragmentPagerAdapter;
import com.droi.film.fragment.CommentListFragment;
import com.droi.film.fragment.FilmIndexFragment;
import com.droi.film.fragment.MainFragment;
import com.droi.film.fragment.ShowingFragment;
import com.droi.film.interfaces.OnFragmentInteractionListener;
import com.droi.film.model.FilmBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmDetailActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @BindView(R.id.tabs)
    public TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    public ViewPager mViewPager;
    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.top_bar_back_btn)
    ImageButton topBarBack;

    private ArrayList<String> mTitleList = new ArrayList<>();//页卡标题集合

    ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTitleList.add("介绍");
        mTitleList.add("短评");
        mTitleList.add("影评");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        for (int i = 0; i < mTitleList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
        }

        fragmentList = new ArrayList<>();
        //Fragment btFragment1 = ShowingFragment.newInstance("showing");
        //Fragment btFragment2 = ShowingFragment.newInstance("coming");
        //Fragment btFragment2 = FilmIndexFragment.newInstance(filmBean);
        //Fragment btFragment3 = new MainFragment();

        FilmBean filmBean = getIntent().getExtras().getParcelable("Film");
        Fragment btFragment1 = FilmIndexFragment.newInstance(filmBean);
        Fragment btFragment2 = CommentListFragment.newInstance(filmBean);
        Fragment btFragment3 = FilmIndexFragment.newInstance(filmBean);
        fragmentList.add(btFragment1);
        fragmentList.add(btFragment2);
        fragmentList.add(btFragment3);

        PagerAdapter mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, mTitleList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        Log.i("test", "oncreate-end");
        topBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*//*

*//*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*//*

        FilmBean filmBean = getIntent().getExtras().getParcelable("Film");
        if (filmBean != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment btFragment2 = FilmIndexFragment.newInstance(filmBean);
            transaction.replace(R.id.id_content, btFragment2);
            transaction.commit();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int action) {

    }
}
