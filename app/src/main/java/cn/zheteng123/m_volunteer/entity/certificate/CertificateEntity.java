package cn.zheteng123.m_volunteer.entity.certificate;

/**
 * Created on 2017/2/25.
 */


public class CertificateEntity {


    /**
     * address : string
     * certificateStatusId : 0
     * id : 0
     * receiver : string
     * receiverPhone : string
     * timestamp : 2017-02-24T16:00:06.314Z
     * userId : 0
     */

    private String address;
    private int certificateStatusId;
    private int id;
    private String receiver;
    private String receiverPhone;
    private String timestamp;
    private int userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCertificateStatusId() {
        return certificateStatusId;
    }

    public void setCertificateStatusId(int certificateStatusId) {
        this.certificateStatusId = certificateStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
