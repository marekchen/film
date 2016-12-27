package com.droi.film;

import android.app.Application;

import com.droi.film.model.Banner;
import com.droi.film.model.CastBean;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiReferenceObject;

/*import com.droi.guide.model.Article;
import com.droi.guide.model.Banner;
import com.droi.guide.model.CloudRelation;
import com.droi.guide.model.Comment;
import com.droi.guide.model.FavoriteRelation;
import com.droi.guide.model.FollowPeopleRelation;
import com.droi.guide.model.FollowQuestionRelation;
import com.droi.guide.model.FileUser;
import com.droi.guide.model.LikeCommentRelation;
import com.droi.guide.model.Question;
import com.droi.guide.qiniu.Auth;
import com.droi.guide.qiniu.Config;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;*/
/*import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.tendcloud.tenddata.TCAgent;*/

/**
 * Created by chenpei on 2016/5/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    //public static Auth auth;

    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
        DroiObject.registerCustomClass(FilmBean.class);
        DroiObject.registerCustomClass(CastBean.class);
        DroiObject.registerCustomClass(FilmUser.class);
        DroiObject.registerCustomClass(Comment.class);
        DroiObject.registerCustomClass(Banner.class);
        DroiPermission permission = DroiPermission.getDefaultPermission();
        if (permission == null) {
            permission = new DroiPermission();
        }
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        DroiPermission.setDefaultPermission(permission);
        /*TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this);
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("testch"));*/
        /*auth = Auth.create(Config.ACCESS_KEY, Config.SECRET_KEY);
        DroiObject.registerCustomClass(Article.class);
        DroiObject.registerCustomClass(Banner.class);
        DroiObject.registerCustomClass(Comment.class);
        DroiObject.registerCustomClass(FavoriteRelation.class);
        DroiObject.registerCustomClass(FavoriteRelation.class);
        DroiObject.registerCustomClass(FollowPeopleRelation.class);
        DroiObject.registerCustomClass(FollowQuestionRelation.class);
        DroiObject.registerCustomClass(FileUser.class);
        DroiObject.registerCustomClass(LikeCommentRelation.class);
        DroiObject.registerCustomClass(Question.class);
        DroiObject.registerCustomClass(CloudRelation.Response.class);
        DroiObject.registerCustomClass(CloudRelation.Request.class);

        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
        DroiPush.initialize(this);

        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
        DroiPush.initialize(this);

        DroiPermission permission = DroiPermission.getDefaultPermission();
        if (permission == null)
            permission = new DroiPermission();
        // 设置默认权限为所有用户可读不可写
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        DroiPermission.setDefaultPermission(permission);*//*auth = Auth.create(Config.ACCESS_KEY, Config.SECRET_KEY);
        DroiObject.registerCustomClass(Article.class);
        DroiObject.registerCustomClass(Banner.class);
        DroiObject.registerCustomClass(Comment.class);
        DroiObject.registerCustomClass(FavoriteRelation.class);
        DroiObject.registerCustomClass(FavoriteRelation.class);
        DroiObject.registerCustomClass(FollowPeopleRelation.class);
        DroiObject.registerCustomClass(FollowQuestionRelation.class);
        DroiObject.registerCustomClass(FileUser.class);
        DroiObject.registerCustomClass(LikeCommentRelation.class);
        DroiObject.registerCustomClass(Question.class);
        DroiObject.registerCustomClass(CloudRelation.Response.class);
        DroiObject.registerCustomClass(CloudRelation.Request.class);

        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
        DroiPush.initialize(this);

        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
        DroiPush.initialize(this);

        DroiPermission permission = DroiPermission.getDefaultPermission();
        if (permission == null)
            permission = new DroiPermission();
        // 设置默认权限为所有用户可读不可写
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        DroiPermission.setDefaultPermission(permission);*/
    }
}
