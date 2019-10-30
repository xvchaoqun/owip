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

    private String unitPost;

    private String executiveLevel;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date workTime;

    private String education;

    private String degree;

    private String school;

    private String major;

    private String electPost;

    private Integer electSession;

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

    public String getUnitPost() {
        return unitPost;
    }

    public void setUnitPost(String unitPost) {
        this.unitPost = unitPost == null ? null : unitPost.trim();
    }

    public String getExecutiveLevel() {
        return executiveLevel;
    }

    public void setExecutiveLevel(String executiveLevel) {
        this.executiveLevel = executiveLevel == null ? null : executiveLevel.trim();
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getElectPost() {
        return electPost;
    }

    public void setElectPost(String electPost) {
        this.electPost = electPost == null ? null : electPost.trim();
    }

    public Integer getElectSession() {
        return electSession;
    }

    public void setElectSession(Integer electSession) {
        this.electSession = electSession;
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