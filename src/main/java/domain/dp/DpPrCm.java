package domain.dp;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class DpPrCm implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private String electSession;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date electTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date endTime;

    private Boolean status;

    private Integer type;

    private Integer sortOrder;

    private String remark;

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

    public String getElectSession() {
        return electSession;
    }

    public void setElectSession(String electSession) {
        this.electSession = electSession == null ? null : electSession.trim();
    }

    public Date getElectTime() {
        return electTime;
    }

    public void setElectTime(Date electTime) {
        this.electTime = electTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}