package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

/**
 * Created by marek on 2016/12/26.
 */

public class CastBean extends DroiObject {
    @DroiExpose
    String name;
    @DroiExpose
    String avatarUrl;
    @DroiExpose
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
