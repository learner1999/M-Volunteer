package cn.zheteng123.m_volunteer.entity.service_record;

/**
 * Created on 2017/2/25.
 */


public class ServiceRecordEntity {

    /**
     * id : 2
     * name : 理发捶背等服务老
     * workingHours : 2.0
     * timestamp : 2017-02-21
     * picture : /public/img/5640d09f-f89b-44ae-97f0-5aa71dd71587.png
     * organization : 李家坞
     * activityStatus : 活动结束
     * distance : 0.0
     * signUp : false
     */

    private int id;
    private String name;
    private double workingHours;
    private String timestamp;
    private String picture;
    private String organization;
    private String activityStatus;
    private double distance;
    private boolean signUp;

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

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
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

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isSignUp() {
        return signUp;
    }

    public void setSignUp(boolean signUp) {
        this.signUp = signUp;
    }
}
