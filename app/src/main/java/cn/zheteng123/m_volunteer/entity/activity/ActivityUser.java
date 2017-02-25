package cn.zheteng123.m_volunteer.entity.activity;

/**
 * Created on 2017/2/25.
 */


public class ActivityUser {

    /**
     * activityId : 0
     * activityUserStatusId : 0
     * id : 0
     * signInStatus : 0
     * star : 0
     * userId : 0
     */

    private int activityId;
    private int activityUserStatusId;
    private int id;
    private int signInStatus;
    private int star;
    private int userId;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getActivityUserStatusId() {
        return activityUserStatusId;
    }

    public void setActivityUserStatusId(int activityUserStatusId) {
        this.activityUserStatusId = activityUserStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSignInStatus() {
        return signInStatus;
    }

    public void setSignInStatus(int signInStatus) {
        this.signInStatus = signInStatus;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
