package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 2016/12/20.
 */

public class FilmBean extends DroiObject {
    @DroiExpose
    String title;        //电影名称
    @DroiExpose
    String images;    //封面图片地址
    @DroiExpose
    float star;         //评分
    @DroiExpose
    int commentsCount;     //评论数量
    @DroiExpose
    String summary;       //简介
    @DroiExpose
    ArrayList<String> genres;       //类型
    @DroiExpose
    ArrayList<String> countries;    //地区
    @DroiExpose
    String releaseTime; //上映时间
    @DroiExpose
    int durationTime;   //影片时长
}
