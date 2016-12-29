package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReferenceObject;

import java.util.ArrayList;

/**
 * Created by marek on 2016/12/20.
 */

public class FilmBean extends DroiObject {
    @DroiExpose
    String title;        //电影名称
    @DroiExpose
    String image;    //封面图片地址
    @DroiExpose
    float star = 0;         //评分
    @DroiExpose
    int commentsCount = 0;     //评论数量
    @DroiExpose
    String summary;       //简介
    @DroiExpose
    ArrayList<String> genres;       //类型
    @DroiExpose
    ArrayList<String> countries;    //地区
    @DroiExpose
    ArrayList<DroiReferenceObject> casts;
    @DroiExpose
    String releaseTime; //上映时间
    @DroiExpose
    public int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public ArrayList<DroiReferenceObject> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<DroiReferenceObject> casts) {
        this.casts = casts;
    }

    public FilmBean() {

    }
}
