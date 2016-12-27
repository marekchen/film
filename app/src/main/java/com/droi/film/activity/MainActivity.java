package com.droi.film.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.adapter.MyFragmentPagerAdapter;
import com.droi.film.fragment.MainFragment;
import com.droi.film.fragment.MineFragment;
import com.droi.film.fragment.ShowingFragment;
import com.droi.film.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTitleList.add("正在上映");
        mTitleList.add("即将上映");
        mTitleList.add("我的电影");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        for (int i = 0; i < mTitleList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
        }

        fragmentList = new ArrayList<>();
        Fragment btFragment1 = ShowingFragment.newInstance("showing");
        Fragment btFragment2 = ShowingFragment.newInstance("coming");
        //Fragment btFragment2 = FilmIndexFragment.newInstance(filmBean);
        Fragment btFragment3 = new MineFragment();

        //Fragment btFragment3 = new MainFragment();

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
    }

    @Override
    public void onFragmentInteraction(int action) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
