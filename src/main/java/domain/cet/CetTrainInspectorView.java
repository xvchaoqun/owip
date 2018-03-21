package domain.cet;

import java.io.Serializable;
import java.util.Date;

public class CetTrainInspectorView implements Serializable {
    private Integer id;

    private Integer trainId;

    private String username;

    private String passwd;

    private String mobile;

    private String realname;

    private Byte passwdChangeType;

    private Byte type;

    private Date createTime;

    private Byte status;

    private Integer finishCourseNum;

    private Integer saveCourseNum;

    private Integer selectedCourseNum;

    private Integer unEvaCourseNum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Byte getPasswdChangeType() {
        return passwdChangeType;
    }

    public void setPasswdChangeType(Byte passwdChangeType) {
        this.passwdChangeType = passwdChangeType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getFinishCourseNum() {
        return finishCourseNum;
    }

    public void setFinishCourseNum(Integer finishCourseNum) {
        this.finishCourseNum = finishCourseNum;
    }

    public Integer getSaveCourseNum() {
        return saveCourseNum;
    }

    public void setSaveCourseNum(Integer saveCourseNum) {
        this.saveCourseNum = saveCourseNum;
    }

    public Integer getSelectedCourseNum() {
        return selectedCourseNum;
    }

    public void setSelectedCourseNum(Integer selectedCourseNum) {
        this.selectedCourseNum = selectedCourseNum;
    }

    public Integer getUnEvaCourseNum() {
        return unEvaCourseNum;
    }

    public void setUnEvaCourseNum(Integer unEvaCourseNum) {
        this.unEvaCourseNum = unEvaCourseNum;
    }
}