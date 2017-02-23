package cn.zheteng123.m_volunteer.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created on 2017/2/17.
 */


public class HomeActivityEntity implements Serializable {

    private int id;  // 活动id

    @SerializedName("name")
    private String title;  // 活动名称
    private double distance;  // 活动地点与当前所处位置的距离
    private String district;  // 活动所处地区

    @SerializedName("recruitedPersonNumber")
    private int enrollNum;  // 已报名人数

    @SerializedName("recruitPersonNumber")
    private int TotalNum;  // 总人数

    private String picture;  // 图片地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getEnrollNum() {
        return enrollNum;
    }

    public void setEnrollNum(int enrollNum) {
        this.enrollNum = enrollNum;
    }

    public int getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(int totalNum) {
        TotalNum = totalNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
