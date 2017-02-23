package cn.zheteng123.m_volunteer.entity.activity;

import java.util.List;

/**
 * Created on 2017/2/22.
 */


public class ActivityDetail {


    /**
     * id : 1
     * organization : 富春街道
     * description : 为老人开展志愿服务，要求会理发，义诊，缝补，磨剪刀，心理咨询，清洗眼镜，量血压
     * recruitTime : 3月3号
     * time : 3月5号
     * address : 西湖区紫荆花路9号紫庭南弄紫金庭园芦荻苑9幢3号商铺
     * workingHours : 8
     * principalName : 卜丽萍
     * principalContact : 13805771623
     * serviceType : 敬老助残
     * volunteers : [{"id":1,"name":"谢鸿涛","idCard":"350581198503129431","sex":0,"age":32,"birthday":479404800000,"politicalStatus":"群众","address":"福建省泉州市石狮市","addressProvince":"福建省","addressCity":"泉州市","workingHours":0,"avatar":"/public/img/default_avatar.jpg"},{"id":2,"name":"昌绍辉 ","idCard":"51190119730115255X","sex":0,"age":44,"birthday":95875200000,"politicalStatus":"群众","address":"四川省巴中市市辖区","addressProvince":"四川省","addressCity":"巴中市","workingHours":0,"avatar":"/public/img/default_avatar.jpg"},{"id":5,"name":"黄德慧","idCard":"421122198407145703","sex":1,"age":33,"birthday":458582400000,"politicalStatus":"群众","address":"湖北省黄冈市红安县","addressProvince":"湖北省","addressCity":"黄冈市","workingHours":0,"avatar":"/public/img/default_avatar.jpg"}]
     */

    private int id;
    private String organization;
    private String description;
    private String recruitTime;
    private String time;
    private String address;
    private int workingHours;
    private String principalName;
    private String principalContact;
    private String serviceType;
    private List<VolunteersBean> volunteers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
         * name : 谢鸿涛
         * idCard : 350581198503129431
         * sex : 0
         * age : 32
         * birthday : 479404800000
         * politicalStatus : 群众
         * address : 福建省泉州市石狮市
         * addressProvince : 福建省
         * addressCity : 泉州市
         * workingHours : 0
         * avatar : /public/img/default_avatar.jpg
         */

        private int id;
        private String name;
        private String idCard;
        private int sex;
        private int age;
        private long birthday;
        private String politicalStatus;
        private String address;
        private String addressProvince;
        private String addressCity;
        private int workingHours;
        private String avatar;

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

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getPoliticalStatus() {
            return politicalStatus;
        }

        public void setPoliticalStatus(String politicalStatus) {
            this.politicalStatus = politicalStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressProvince() {
            return addressProvince;
        }

        public void setAddressProvince(String addressProvince) {
            this.addressProvince = addressProvince;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
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
    }
}
