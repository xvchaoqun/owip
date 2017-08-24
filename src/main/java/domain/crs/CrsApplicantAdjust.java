package domain.crs;

import java.io.Serializable;
import java.util.Date;

public class CrsApplicantAdjust implements Serializable {
    private Integer id;

    private Integer userId;

    private Date adjustTime;

    private String prePostIds;

    private String afterPostIds;

    private Integer opUserId;

    private String ip;

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

    public Date getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(Date adjustTime) {
        this.adjustTime = adjustTime;
    }

    public String getPrePostIds() {
        return prePostIds;
    }

    public void setPrePostIds(String prePostIds) {
        this.prePostIds = prePostIds == null ? null : prePostIds.trim();
    }

    public String getAfterPostIds() {
        return afterPostIds;
    }

    public void setAfterPostIds(String afterPostIds) {
        this.afterPostIds = afterPostIds == null ? null : afterPostIds.trim();
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}