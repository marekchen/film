package com.droi.film;

import android.app.Application;

import com.droi.film.model.Banner;
import com.droi.film.model.CastBean;
import com.droi.film.model.Comment;
import com.droi.film.model.FilmBean;
import com.droi.film.model.FilmUser;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;

/**
 * Created by chenpei on 2016/5/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

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
        
        DroiAnalytics.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
        DroiPush.initialize(this);
    }
}
