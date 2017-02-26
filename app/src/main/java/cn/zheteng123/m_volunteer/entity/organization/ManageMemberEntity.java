package cn.zheteng123.m_volunteer.entity.organization;

/**
 * Created on 2017/2/26.
 */


public class ManageMemberEntity {


    /**
     * id : 1
     * name : 谢鸿涛
     * workingHours : 93
     * avatar : /public/img/default_avatar.jpg
     * certificateStatusId : 1
     */

    private int id;
    private String name;
    private int workingHours;
    private String avatar;
    private Integer certificateStatusId;

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

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCertificateStatusId() {
        return certificateStatusId;
    }

    public void setCertificateStatusId(Integer certificateStatusId) {
        this.certificateStatusId = certificateStatusId;
    }
}
