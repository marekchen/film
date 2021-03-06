package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;

/**
 * Created by chenpei on 16/8/31.
 */
public class FilmUser extends DroiUser {

    @DroiExpose
    public DroiFile avatar;
    @DroiExpose
    public String userName; //nick name

    public FilmUser() {

    }
}
