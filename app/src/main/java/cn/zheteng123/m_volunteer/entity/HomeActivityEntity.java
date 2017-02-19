package cn.zheteng123.m_volunteer.entity;

/**
 * Created on 2017/2/17.
 */


public class HomeActivityEntity {

    private String title;  // 活动名称
    private double distance;  // 活动地点与当前所处位置的距离
    private String district;  // 活动所处地区
    private int enrollNum;  // 已报名人数
    private int TotalNum;  // 总人数

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
}
