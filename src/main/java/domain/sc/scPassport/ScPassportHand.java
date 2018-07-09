package domain.sc.scPassport;

import java.io.Serializable;
import java.util.Date;

public class ScPassportHand implements Serializable {
    private Integer id;

    private Integer userId;

    private Date appointDate;

    private Byte addType;

    private String remark;

    private Byte status;

    private Boolean isKeep;

    private Date addTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public Byte getAddType() {
        return addType;
    }

    public void setAddType(Byte addType) {
        this.addType = addType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getIsKeep() {
        return isKeep;
    }

    public void setIsKeep(Boolean isKeep) {
        this.isKeep = isKeep;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}