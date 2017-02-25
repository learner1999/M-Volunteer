package cn.zheteng123.m_volunteer.entity.organization;

/**
 * Created on 2017/2/25.
 */


public class OrganizationEntity {

    /**
     * id : 3
     * email : xsh@126.com
     * name : 富春街道
     * foundingDate : 1487991564000
     * volunteerNumber : 2
     * avatar : /public/img/default_avatar.jpg
     */

    private int id;
    private String email;
    private String name;
    private long foundingDate;
    private int volunteerNumber;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(long foundingDate) {
        this.foundingDate = foundingDate;
    }

    public int getVolunteerNumber() {
        return volunteerNumber;
    }

    public void setVolunteerNumber(int volunteerNumber) {
        this.volunteerNumber = volunteerNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
