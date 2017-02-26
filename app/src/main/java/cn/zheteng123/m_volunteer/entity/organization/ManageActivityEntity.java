package cn.zheteng123.m_volunteer.entity.organization;

import java.util.Date;

/**
 * Created on 2017/2/25.
 */


public class ManageActivityEntity {


    /**
     * id : 28
     * name : 环保助力中国梦
     * timestamp : 2017-02-25
     * picture : /public/img/3db4436f-976b-4876-b1bd-af31c80d9e79.jpg
     * description : 正在招募
     * distance : 0
     * signUp : false
     */

    private int id;
    private String name;
    private Date timestamp;
    private String picture;
    private String description;
    private int distance;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isSignUp() {
        return signUp;
    }

    public void setSignUp(boolean signUp) {
        this.signUp = signUp;
    }
}
