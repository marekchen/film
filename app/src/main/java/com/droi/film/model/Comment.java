package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by chenpei on 16/9/3.
 */
public class Comment extends DroiObject {
    @DroiExpose
    public String refId;
    @DroiExpose
    public int type;//1:短评、2:影评
    @DroiExpose
    public String commenterId;
    @DroiReference
    public FilmUser commenter;
    @DroiExpose
    public String comment;
    @DroiExpose
    public float star = -1;
    @DroiExpose
    public String title;
    @DroiExpose
    public int usefullNum = 0;
    @DroiExpose
    public int uselessNum = 0;

    //短评
    public Comment(String refId, String comment, FilmUser commenter) {
        this.refId = refId;
        this.type = 1;
        this.comment = comment;
        this.commenterId = commenter.getObjectId();
        this.commenter = commenter;
    }

    //短评
    public Comment(String refId, String comment, FilmUser commenter, int star) {
        this.refId = refId;
        this.type = 2;
        this.comment = comment;
        this.commenterId = commenter.getObjectId();
        this.commenter = commenter;
        this.star = star;
    }

    public Comment() {

    }
}
