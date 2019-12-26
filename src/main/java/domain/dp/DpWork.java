package domain.dp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DpWork implements Serializable {

    private List<DpWork> subDpWorks;

    public List<DpWork> getSubDpWorks() {
        return subDpWorks;
    }

    public void setSubDpWorks(List<DpWork> subDpWorks) {
        this.subDpWorks = subDpWorks;
    }

    private Integer id;

    private Integer fid;

    private Boolean isEduWork;

    private Integer subWorkCount;

    private Integer userId;

    private Date startTime;

    private Date endTime;

    private String detail;

    private String unitIds;

    private String workTypes;

    private Boolean isCadre;

    private String remark;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Boolean getIsEduWork() {
        return isEduWork;
    }

    public void setIsEduWork(Boolean isEduWork) {
        this.isEduWork = isEduWork;
    }

    public Integer getSubWorkCount() {
        return subWorkCount;
    }

    public void setSubWorkCount(Integer subWorkCount) {
        this.subWorkCount = subWorkCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds == null ? null : unitIds.trim();
    }

    public String getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(String workTypes) {
        this.workTypes = workTypes == null ? null : workTypes.trim();
    }

    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
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
}