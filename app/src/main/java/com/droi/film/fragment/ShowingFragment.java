package com.droi.film.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowingFragment extends Fragment {
    private static final String ARG_PARAM1 = "tab";

    private String tab;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    MovieAdapter movieAdapter;

    @BindView(R.id.movie_rv)
    RecyclerView movieGridView;

    public ShowingFragment() {
        // Required empty public constructor
    }

    public static ShowingFragment newInstance(String tab) {
        ShowingFragment fragment = new ShowingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tab = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_showing, container, false);
        unbinder = ButterKnife.bind(this, view);

        FilmBean filmBean = new FilmBean();
        filmBean.setTitle("摆渡人");
        ArrayList genres = new ArrayList<String>();
        genres.add("喜剧");
        genres.add("爱情");
        filmBean.setGenres(genres);
        filmBean.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2390687452.jpg");
        ArrayList countries = new ArrayList<String>();
        countries.add("中国大陆");
        countries.add("香港");
        filmBean.setCountries(countries);
        filmBean.setCommentsCount(1000);
        filmBean.setReleaseTime("2016-12-23");
        filmBean.setStar(4.4f);
        filmBean.setSummary("摆渡人是城市里的超级英雄，摆渡就是把人从痛苦中解救出来。用快乐和温暖，抵抗这个世界的悲伤。\n酒吧老板陈末（梁朝伟 饰）和合伙人管春（金城武 饰）就是这座城市的“金牌摆渡人”，他们平时看起来吊儿郎当，却从不对每位需要帮助的人说拒绝，只要你“预约摆渡”，刀山火海都会“使命必达”。邻居女孩小玉（杨颖 饰）为了偶像马力（陈奕迅 饰），预约了他们的服务，但在帮助小玉挑战整个城市的过程中，陈末和管春也逐渐发现了自己躲不过的问题。从欢天喜地的生活，到惊天动地的疯狂，摆渡人最辉煌的篇章，从这里开启。");

        FilmBean filmBean2 = new FilmBean();
        filmBean2.setTitle("铁道飞虎");
        filmBean2.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2404720316.jpg");

        FilmBean filmBean3 = new FilmBean();
        filmBean3.setTitle("长城");
        filmBean3.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2394573821.jpg");

        FilmBean filmBean4 = new FilmBean();
        filmBean4.setTitle("血战钢锯岭");
        filmBean4.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2397337958.jpg");

        FilmBean filmBean5 = new FilmBean();
        filmBean5.setTitle("罗曼蒂克消亡史");
        filmBean5.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2404553168.jpg");

        FilmBean filmBean6 = new FilmBean();
        filmBean6.setTitle("你的名字。");
        filmBean6.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2395733377.jpg");

        FilmBean filmBean7 = new FilmBean();
        filmBean7.setTitle("绝对控制");
        filmBean7.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2405285938.jpg");

        FilmBean filmBean8 = new FilmBean();
        filmBean8.setTitle("海洋奇缘");
        filmBean8.setImage("http://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2397960879.jpg");

        FilmBean filmBean10 = new FilmBean();
        filmBean10.setTitle("掠夺者");
        filmBean10.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2404556021.jpg");

        FilmBean filmBean11 = new FilmBean();
        filmBean11.setTitle("萨利机长");
        filmBean11.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2403319543.jpg");

        FilmBean filmBean12 = new FilmBean();
        filmBean12.setTitle("神奇动物在哪里");
        filmBean12.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2392444121.jpg");

        FilmBean filmBean13 = new FilmBean();
        filmBean13.setTitle("我在故宫修文物");
        filmBean13.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2396361760.jpg");

        FilmBean filmBean14 = new FilmBean();
        filmBean14.setTitle("失心者");
        filmBean14.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2405533771.jpg");

        FilmBean filmBean15 = new FilmBean();
        filmBean15.setTitle("佩小姐的奇幻城堡");
        filmBean15.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2398173791.jpg");

        FilmBean filmBean16 = new FilmBean();
        filmBean16.setTitle("28岁未成年");
        filmBean16.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2402824160.jpg");

        FilmBean filmBean17 = new FilmBean();
        filmBean17.setTitle("有迹可循");
        filmBean17.setImage("http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2403079835.jpg");
        ArrayList films = new ArrayList<FilmBean>();

        films.add(filmBean);
        films.add(filmBean2);
        films.add(filmBean3);
        films.add(filmBean4);
        films.add(filmBean5);
        films.add(filmBean6);
        films.add(filmBean7);
        films.add(filmBean8);
        //films.add(filmBean9);
        films.add(filmBean10);
        films.add(filmBean11);
        films.add(filmBean12);
        films.add(filmBean13);
        films.add(filmBean14);
        films.add(filmBean15);
        films.add(filmBean16);
        films.add(filmBean17);
        movieAdapter = new MovieAdapter(getActivity(), films);
        movieGridView.setAdapter(movieAdapter);

        /*DroiQuery query = DroiQuery.Builder.newBuilder().query(FilmBean.class).build();
        query.runQueryInBackground(new DroiQueryCallback<FilmBean>() {
            @Override
            public void result(List<FilmBean> list, DroiError droiError) {
                Log.i("chenpei", "query:" + droiError.toString());
                if (droiError.isOk() && list.size() > 0) {
                    *//*Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
                    intent.putExtra("Film", list.get(0));
                    startActivity(intent);*//*
                    movieAdapter = new MovieAdapter(getActivity(), list);
                    movieGridView.setAdapter(movieAdapter);
                }
            }
        });*/


        /*Button button2 = (Button) view.findViewById(R.id.detail);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroiQuery query = DroiQuery.Builder.newBuilder().query(FilmBean.class).build();
                query.runQueryInBackground(new DroiQueryCallback<DroiObject>() {
                    @Override
                    public void result(List<DroiObject> list, DroiError droiError) {
                        Log.i("chenpei", "query:" + droiError.toString());
                        if (droiError.isOk() && list.size() > 0) {
                            Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
                            intent.putExtra("Film", list.get(0));
                            startActivity(intent);
                        }
                    }
                });

            }
        });*/
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        //调用以下方法让RecyclerView的第一个条目仅为1列
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
                //而如果不是0，则说明不是Header，就占用1列即可
                return movieAdapter.isHeader(position) ? layoutManager.getSpanCount() : 1;
            }
        });

        //把LayoutManager设置给RecyclerView
        movieGridView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
