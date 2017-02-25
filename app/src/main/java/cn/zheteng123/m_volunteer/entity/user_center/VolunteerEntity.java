package cn.zheteng123.m_volunteer.entity.user_center;

/**
 * Created on 2017/2/23.
 */


public class VolunteerEntity {


    /**
     * id : 2
     * email : wutao
     * name : 昌绍辉
     * age : 44
     * workingHours : 65
     * avatar : /public/img/default_avatar.jpg
     */

    private int id;
    private String email;
    private String name;
    private int age;
    private double workingHours;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
