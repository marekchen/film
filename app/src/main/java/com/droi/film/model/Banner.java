package com.droi.film.model;

import android.net.Uri;

import com.droi.sdk.core.DroiExpose;
/*
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;*/

/**
 * Created by chenpei on 16/8/31.
 */
public class Banner {
    @DroiExpose
    public String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /*    @DroiReference
    public DroiFile img;
    @DroiReference
    public Article ref;
    public Uri imgUri;*/
}
