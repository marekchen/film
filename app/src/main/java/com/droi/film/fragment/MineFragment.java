package com.droi.film.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.activity.AboutUsActivity;
import com.droi.film.activity.CommentListActivity;
import com.droi.film.activity.LoginActivity;
import com.droi.film.activity.ProfileActivity;

import com.droi.film.model.CastBean;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;
import com.droi.film.view.CircleImageView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiGroup;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenpei on 2016/5/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "MineFragment";
    private Context mContext;
    @BindView(R.id.head_icon)
    CircleImageView titleImg;
    @BindView(R.id.user_name)
    TextView nameTextView;
    @BindView(R.id.push_switch)
    Switch pushSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initUI(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
        DroiAnalytics.onFragmentStart(getActivity(), "MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "MineFragment");
    }

    /**
     * 当登录成功或者登出时刷新View
     */
    private void refreshView() {
        FilmUser user = DroiUser.getCurrentUser(FilmUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            nameTextView.setText(user.getUserId());
            if (user.avatar != null) {
                user.avatar.getInBackground(new DroiCallback<byte[]>() {
                    @Override
                    public void result(byte[] bytes, DroiError error) {
                        if (error.isOk()) {
                            if (bytes == null) {
                                Log.i(TAG, "bytes == null");
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                titleImg.setImageBitmap(bitmap);
                            }
                        }
                    }
                }, null);
            }
        } else {
            titleImg.setImageResource(R.drawable.profile_default_icon);
            nameTextView.setText(R.string.fragment_mine_login);
        }
    }

    private void initUI(View view) {
        view.findViewById(R.id.mine_frag_follow).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_comment).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_update).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_feedback).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_upload).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_push).setOnClickListener(this);
        view.findViewById(R.id.mine_about_us).setOnClickListener(this);
        view.findViewById(R.id.head_icon).setOnClickListener(this);
        view.findViewById(R.id.right_layout).setOnClickListener(this);
        pushSwitch.setChecked(DroiPush.getPushEnabled(mContext));
        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DroiPush.setPushEnabled(mContext, isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        FilmUser user = DroiUser.getCurrentUser(FilmUser.class);
        switch (v.getId()) {
            case R.id.head_icon:

            case R.id.right_layout:
                if (user != null && user.isAuthorized() && !user.isAnonymous()) {
                    toProfile();
                } else {
                    toLogin();
                }
                break;
            case R.id.mine_frag_comment:
                Intent answerIntent = new Intent(getActivity(), CommentListActivity.class);
                startActivity(answerIntent);
                break;
            case R.id.mine_frag_update:
                //手动更新
                DroiUpdate.manualUpdate(mContext);
                break;
            case R.id.mine_frag_feedback:
                //自定义部分颜色
                DroiFeedback.setTitleBarColor(getResources().getColor(R.color.top_bar_background));
                DroiFeedback.setSendButtonColor(getResources().getColor(R.color.top_bar_background),
                        getResources().getColor(R.color.top_bar_background));
                //打开反馈页面
                DroiFeedback.callFeedback(mContext);
                break;
            case R.id.mine_about_us:
                fetch();
                /*Log.i("test", "about");
                Intent aboutUsIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(aboutUsIntent);*/
                break;
            case R.id.mine_frag_upload:
                Log.i("TEST", "mine_frag_upload");
                fetch();
                //uploadBanner();
                //uploadAppInfo();
                //uploadAppType();
                //uploadOfficialGuide();
                /*new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        upload1();
                    }
                }.start();*/
/*                Comment comment = new Comment("170c4787bbc4d5398c44965f", "dddddd", DroiUser.getCurrentUser(FilmUser.class), 3);
                DroiCloud.callCloudServiceInBackground("compute_star.lua", comment, new DroiCallback<Comment>() {
                    @Override
                    public void result(Comment droiObject, DroiError droiError) {
                        Log.i("chenpei", droiError.toString());
                    }
                }, Comment.class);*/
                break;
            default:
                break;
        }
    }

    void add() {

        DroiQuery query = DroiQuery.Builder.newBuilder().cloudStorage().query(DroiGroup.class)
                .where("Name", DroiCondition.Type.EQ, "manager").build();
        query.runQueryInBackground(new DroiQueryCallback<DroiGroup>() {
            @Override
            public void result(final List<DroiGroup> list, final DroiError droiError) {
                Log.i("chenpei", "0" + droiError.toString());
                DroiGroup droiGroup;
                if (list.size() > 0) {
                    Log.i("chenpei", "1");
                    droiGroup = list.get(0);
                } else {
                    Log.i("chenpei", "2");
                    droiGroup = new DroiGroup("manager");
                }
                DroiPermission droiPermission = new DroiPermission();
                droiGroup.addUser(DroiUser.getCurrentUser().getObjectId());
                droiGroup.saveInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, final DroiError droiError) {
                        Log.i("chenpei", "3" + droiError.toString());
                    }
                });

            }
        });
    }

    void fetch() {
        final DroiUser droiUser = DroiUser.getCurrentUser();
        if (droiUser != null && droiUser.isAuthorized() && !droiUser.isAnonymous()) {
            if (droiUser.getUserId().equals("admin")) {
                return;
            }
            DroiGroup.getGroupIdsByUserObjectIdInBackground(droiUser.getObjectId(), new DroiCallback<List<String>>() {
                @Override
                public void result(List<String> list, DroiError droiError) {
                    Log.i("chenpei", ">>>size" + list.size());
                    Log.i("chenpei", ">>>" + droiError.toString());
                    if (droiError.isOk()) {
                        for (String string :
                                list) {
                            Log.i("chenpei", ">>>" + string);
                            if (string.equals("875941fea855e99f2df23a4e")) {
                                Log.i("chenpei", ">>>" + true);
                            }
                        }
                    } else {

                    }
                }
            });
        } else {
            Log.i("chenpei", ">>>2");
        }
    }

    /**
     * 转到登录页面
     */
    private void toLogin() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);
    }

    /**
     * 转到个人信息页面
     */
    private void toProfile() {
        Intent profileIntent = new Intent(mContext, ProfileActivity.class);
        startActivity(profileIntent);
    }

    void upload1() {
        String url = "http://api.douban.com/v2/movie/in_theaters";
        try {
            String json = run(url);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            for (int i = 0; i < jsonArray.length(); i++) {
                FilmBean filmBean = new FilmBean();
                JSONObject jo = jsonArray.getJSONObject(i);

                String jsonAlt = run("http://api.douban.com/v2/movie/subject/" + jo.getString("id"));
                JSONObject movie = new JSONObject(jsonAlt);

                filmBean.setTitle(movie.getString("title"));
                filmBean.setImage(movie.getJSONObject("images").getString("large"));

                JSONArray directors = movie.getJSONArray("directors");
                ArrayList<DroiReferenceObject> castList = new ArrayList<>();
                for (int j = 0; j < directors.length(); j++) {
                    JSONObject director = directors.getJSONObject(j);
                    CastBean castBean = new CastBean();
                    castBean.setName(director.getString("name"));
                    castBean.setAvatarUrl(director.getJSONObject("avatars").getString("large"));
                    castBean.setType(0);
                    DroiReferenceObject ref1 = new DroiReferenceObject();
                    ref1.setDroiObject(castBean);
                    castList.add(ref1);
                }
                JSONArray casts = movie.getJSONArray("casts");
                for (int j = 0; j < casts.length(); j++) {
                    JSONObject director = casts.getJSONObject(j);
                    CastBean castBean = new CastBean();
                    castBean.setName(director.getString("name"));
                    castBean.setAvatarUrl(director.getJSONObject("avatars").getString("large"));
                    castBean.setType(1);
                    DroiReferenceObject ref1 = new DroiReferenceObject();
                    ref1.setDroiObject(castBean);
                    castList.add(ref1);
                }
                filmBean.setCasts(castList);

                JSONArray genresObject = movie.getJSONArray("genres");
                ArrayList<String> genres = new ArrayList<>();
                for (int j = 0; j < genresObject.length(); j++) {
                    String genre = genresObject.getString(j);
                    genres.add(genre);
                }
                filmBean.setGenres(genres);

                JSONArray countriesObject = movie.getJSONArray("countries");
                ArrayList<String> countries = new ArrayList<>();
                for (int j = 0; j < countriesObject.length(); j++) {
                    String genre = countriesObject.getString(j);
                    countries.add(genre);
                }
                filmBean.setCountries(countries);

                filmBean.setSummary(movie.getString("summary"));
                filmBean.setReleaseTime(movie.getString("year"));
                filmBean.type = 1;
                filmBean.saveInBackground(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    void upload() {
        FilmBean filmBean = new FilmBean();
        filmBean.setTitle("摆渡人");
        ArrayList genres = new ArrayList<String>();
        genres.add("喜剧");
        genres.add("爱情");
        filmBean.setGenres(genres);
        filmBean.setImage("http://img7.doubanio.com/view/movie_poster_cover/spst/public/p2390687452.jpg");
        ArrayList countries = new ArrayList<String>();
        countries.add("中国大陆");
        countries.add("香港");
        filmBean.setCountries(countries);
        filmBean.setCommentsCount(1000);
        filmBean.setReleaseTime("2016-12-23");
        filmBean.setStar(4.4f);
        filmBean.setSummary("摆渡人是城市里的超级英雄，摆渡就是把人从痛苦中解救出来。用快乐和温暖，抵抗这个世界的悲伤。\n酒吧老板陈末（梁朝伟 饰）和合伙人管春（金城武 饰）就是这座城市的“金牌摆渡人”，他们平时看起来吊儿郎当，却从不对每位需要帮助的人说拒绝，只要你“预约摆渡”，刀山火海都会“使命必达”。邻居女孩小玉（杨颖 饰）为了偶像马力（陈奕迅 饰），预约了他们的服务，但在帮助小玉挑战整个城市的过程中，陈末和管春也逐渐发现了自己躲不过的问题。从欢天喜地的生活，到惊天动地的疯狂，摆渡人最辉煌的篇章，从这里开启。");

        CastBean castBean = new CastBean();
        castBean.setName("梁朝伟");
        castBean.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/33525.jpg");
        CastBean castBean2 = new CastBean();
        castBean2.setName("金城武");
        castBean2.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/6925.jpg");
        CastBean castBean3 = new CastBean();
        castBean3.setName("陈奕迅");
        castBean3.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/20210.jpg");
        CastBean castBean4 = new CastBean();
        castBean4.setName("杨颖");
        castBean4.setAvatarUrl("http://img7.doubanio.com/img/celebrity/large/50831.jpg");
        ArrayList<DroiReferenceObject> casts = new ArrayList<>();
        DroiReferenceObject ref1 = new DroiReferenceObject();
        ref1.setDroiObject(castBean);
        DroiReferenceObject ref2 = new DroiReferenceObject();
        ref2.setDroiObject(castBean2);
        DroiReferenceObject ref3 = new DroiReferenceObject();
        ref3.setDroiObject(castBean3);
        DroiReferenceObject ref4 = new DroiReferenceObject();
        ref4.setDroiObject(castBean4);
        casts.add(ref1);
        casts.add(ref2);
        casts.add(ref3);
        casts.add(ref4);
        filmBean.setCasts(casts);
        filmBean.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                Log.i("chenpei", droiError.toString());
            }
        });
    }
}
