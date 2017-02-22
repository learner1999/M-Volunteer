package cn.zheteng123.m_volunteer.entity.login;

/**
 * Created on 2017/2/22.
 */


public class Role {

    /**
     * id : 1
     * name : volunteer
     * type : ROLE_VOL
     */

    private int id;
    private String name;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
