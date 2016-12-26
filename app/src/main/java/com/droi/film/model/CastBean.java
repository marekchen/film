package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReferenceObject;

/**
 * Created by marek on 2016/12/26.
 */

public class CastBean extends DroiObject {
    @DroiExpose
    String name;
    @DroiExpose
    String avatarUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
