package com.droi.film.model;

import android.os.Parcelable;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;

/**
 * Created by chenpei on 16/8/31.
 */
public class FileUser extends DroiUser implements Parcelable {

    @DroiExpose
    public DroiFile avatar;
    @DroiExpose
    public String userName; //nick name

    public FileUser() {

    }
}
