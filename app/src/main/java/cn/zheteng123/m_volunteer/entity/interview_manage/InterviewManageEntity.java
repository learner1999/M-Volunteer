package cn.zheteng123.m_volunteer.entity.interview_manage;

/**
 * Created on 2017/2/27.
 */


public class InterviewManageEntity {


    /**
     * username : wutao
     * avatar : /public/img/e3e8f15a-56fd-426f-aaa7-bb9698a6f9c9.jpg
     * interviewStatus : 1
     * activityName : 环保助力中国梦
     * activityUserId : 52
     */

    private String username;
    private String avatar;
    private int interviewStatus;
    private String activityName;
    private int activityUserId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(int interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityUserId() {
        return activityUserId;
    }

    public void setActivityUserId(int activityUserId) {
        this.activityUserId = activityUserId;
    }
}
