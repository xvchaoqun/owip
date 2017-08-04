package domain.crs;

import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CrsPost implements Serializable {

    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }

    private Integer id;

    private Integer year;

    private Byte type;

    private Integer seq;

    private String name;

    private Integer adminLevel;

    private Integer unitId;

    private String notice;

    private String requirement;

    private String qualification;

    private Integer postRequireId;

    private Date startTime;

    private Date endTime;

    private Byte enrollStatus;

    private Date meetingTime;

    private String meetingAddress;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
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

    public Integer getPostRequireId() {
        return postRequireId;
    }

    public void setPostRequireId(Integer postRequireId) {
        this.postRequireId = postRequireId;
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

    public Byte getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(Byte enrollStatus) {
        this.enrollStatus = enrollStatus;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress == null ? null : meetingAddress.trim();
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