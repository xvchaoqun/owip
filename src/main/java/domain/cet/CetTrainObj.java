package domain.cet;

import java.io.Serializable;
import java.util.Date;

public class CetTrainObj implements Serializable {
    private Integer id;

    private Integer objId;

    private Integer userId;

    private Integer trainCourseId;

    private Boolean canQuit;

    private Boolean isFinished;

    private Date signTime;

    private Date signOutTime;

    private Byte signType;

    private Date chooseTime;

    private Integer chooseUserId;

    private String ip;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTrainCourseId() {
        return trainCourseId;
    }

    public void setTrainCourseId(Integer trainCourseId) {
        this.trainCourseId = trainCourseId;
    }

    public Boolean getCanQuit() {
        return canQuit;
    }

    public void setCanQuit(Boolean canQuit) {
        this.canQuit = canQuit;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public Byte getSignType() {
        return signType;
    }

    public void setSignType(Byte signType) {
        this.signType = signType;
    }

    public Date getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(Date chooseTime) {
        this.chooseTime = chooseTime;
    }

    public Integer getChooseUserId() {
        return chooseUserId;
    }

    public void setChooseUserId(Integer chooseUserId) {
        this.chooseUserId = chooseUserId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}