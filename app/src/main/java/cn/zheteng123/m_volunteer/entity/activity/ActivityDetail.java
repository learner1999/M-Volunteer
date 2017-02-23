package cn.zheteng123.m_volunteer.entity.activity;

import java.util.List;

/**
 * Created on 2017/2/22.
 */


public class ActivityDetail {


    /**
     * id : 1
     * addressStreet : 紫荆花路
     * address : 西湖区紫荆花路9号紫庭南弄紫金庭园芦荻苑9幢3号商铺
     * serviceType : 敬老助残
     * recruitTime : 3月3号
     * time : 3月5号
     * principalName : 卜丽萍
     * principalContact : 13805771623
     * workingHours : 8.0
     * organizationId : 3
     * description : 为老人开展志愿服务，要求会理发，义诊，缝补，磨剪刀，心理咨询，清洗眼镜，量血压
     * organization : 富春街道
     * volunteers : [{"id":1,"avatar":"/public/img/default_avatar.jpg"},{"id":2,"avatar":"/public/img/e3e8f15a-56fd-426f-aaa7-bb9698a6f9c9.jpg"},{"id":5,"avatar":"/public/img/default_avatar.jpg"}]
     */

    private int id;
    private String addressStreet;
    private String address;
    private String serviceType;
    private String recruitTime;
    private String time;
    private String principalName;
    private String principalContact;
    private double workingHours;
    private int organizationId;
    private String description;
    private String organization;
    private List<VolunteersBean> volunteers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRecruitTime() {
        return recruitTime;
    }

    public void setRecruitTime(String recruitTime) {
        this.recruitTime = recruitTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalContact() {
        return principalContact;
    }

    public void setPrincipalContact(String principalContact) {
        this.principalContact = principalContact;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<VolunteersBean> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<VolunteersBean> volunteers) {
        this.volunteers = volunteers;
    }

    public static class VolunteersBean {
        /**
         * id : 1
         * avatar : /public/img/default_avatar.jpg
         */

        private int id;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
