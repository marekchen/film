package com.droi.film.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiReferenceObject;

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
/*    @DroiExpose
    ArrayList casts;*/
    @DroiExpose
    String releaseTime; //上映时间
    @DroiExpose
    int durationTime;   //影片时长

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

/*    public ArrayList<Object> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Object> casts) {
        this.casts = casts;
    }*/

    public FilmBean() {

    }
}
