package domain.train;

import java.io.Serializable;
import java.util.Date;

public class TrainInspector implements Serializable {
    private Integer id;

    private Integer trainId;

    private String username;

    private String passwd;

    private Byte passwdChangeType;

    private Byte type;

    private Date createTime;

    private Integer finishCourseNum;

    private Byte status;

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

    public Integer getFinishCourseNum() {
        return finishCourseNum;
    }

    public void setFinishCourseNum(Integer finishCourseNum) {
        this.finishCourseNum = finishCourseNum;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}