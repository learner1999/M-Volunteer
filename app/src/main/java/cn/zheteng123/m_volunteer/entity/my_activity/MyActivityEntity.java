package cn.zheteng123.m_volunteer.entity.my_activity;

/**
 * Created on 2017/2/23.
 */


public class MyActivityEntity {


    /**
     * id : 2
     * name : 理发捶背等服务老
     * timestamp : 2017-02-21
     * picture : /public/img/5640d09f-f89b-44ae-97f0-5aa71dd71587.png
     * organization : 富春街道
     * interviewStatus : 面试通过
     * activityStatus : 活动进行中
     */

    private int id;
    private String name;
    private String timestamp;
    private String picture;
    private String organization;
    private String interviewStatus;
    private String activityStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
}
