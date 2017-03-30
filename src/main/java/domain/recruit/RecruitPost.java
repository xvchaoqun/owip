package domain.recruit;

import java.io.Serializable;
import java.util.Date;

public class RecruitPost implements Serializable {
    private Integer id;

    private Integer year;

    private String name;

    private Integer adminLevel;

    private Integer unitId;

    private String requirement;

    private String qualification;

    private Byte signStatus;

    private Boolean meetingStatus;

    private Boolean committeeStatus;

    private String remark;

    private Date createTime;

    private Boolean isPublish;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement == null ? null : requirement.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }

    public Byte getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Byte signStatus) {
        this.signStatus = signStatus;
    }

    public Boolean getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(Boolean meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public Boolean getCommitteeStatus() {
        return committeeStatus;
    }

    public void setCommitteeStatus(Boolean committeeStatus) {
        this.committeeStatus = committeeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Boolean isPublish) {
        this.isPublish = isPublish;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}