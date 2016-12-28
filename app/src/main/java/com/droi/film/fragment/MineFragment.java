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
import android.widget.Switch;
import android.widget.TextView;

import com.droi.film.R;
import com.droi.film.activity.LoginActivity;
import com.droi.film.activity.ProfileActivity;

import com.droi.film.model.CastBean;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;
import com.droi.film.view.CircleImageView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.sdk.core.DroiUser;
/*import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;*/

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        //DroiAnalytics.onFragmentStart(getActivity(), "MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        //DroiAnalytics.onFragmentEnd(getActivity(), "MineFragment");
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
        view.findViewById(R.id.mine_frag_answer).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_update).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_feedback).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_upload).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_push).setOnClickListener(this);
        view.findViewById(R.id.mine_about_us).setOnClickListener(this);
        view.findViewById(R.id.head_icon).setOnClickListener(this);
        view.findViewById(R.id.right_layout).setOnClickListener(this);
        /*pushSwitch.setChecked(DroiPush.getPushEnabled(mContext));
        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DroiPush.setPushEnabled(mContext, isChecked);
            }
        });*/
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
            case R.id.mine_frag_follow:
                Log.i("test", "follow");
                /*Intent followIntent = new Intent(getActivity(), MyFollowActivity.class);
                startActivity(followIntent);*/
                break;
            case R.id.mine_frag_answer:
                Log.i("test", "answer");
                /*Intent answerIntent = new Intent(getActivity(), MyAnswerActivity.class);
                startActivity(answerIntent);*/
                break;
/*            case R.id.mine_frag_update:
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
                Log.i("test", "about");
                Intent aboutUsIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(aboutUsIntent);
                break;*/
            case R.id.mine_frag_upload:
                Log.i("TEST", "mine_frag_upload");
                //uploadBanner();
                //uploadAppInfo();
                //uploadAppType();
                //uploadOfficialGuide();
                upload();
                break;
            default:
                break;
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

    void upload(){
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
