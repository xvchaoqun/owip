package domain.cet;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetTrainObjView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer objId;

    private Integer userId;

    private Integer trainCourseId;

    private Boolean canQuit;

    private Byte isFinished;

    private Date signTime;

    private Date signOutTime;

    private Byte signType;

    private Date chooseTime;

    private Integer chooseUserId;

    private String ip;

    private String remark;

    private Integer projectId;

    private Integer traineeTypeId;

    private String projectName;

    private Byte projectType;

    private Boolean isPartyProject;

    private Date startDate;

    private Date endDate;

    private Integer cetPartyId;

    private Byte projectStatus;

    private Boolean projectIsDeleted;

    private Boolean isQuit;

    private Integer planId;

    private Integer trainId;

    private Integer courseId;

    private BigDecimal period;

    private Boolean isOnline;

    private Integer year;

    private String chooseUserCode;

    private String chooseUserName;

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

    public Byte getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Byte isFinished) {
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Byte getProjectType() {
        return projectType;
    }

    public void setProjectType(Byte projectType) {
        this.projectType = projectType;
    }

    public Boolean getIsPartyProject() {
        return isPartyProject;
    }

    public void setIsPartyProject(Boolean isPartyProject) {
        this.isPartyProject = isPartyProject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCetPartyId() {
        return cetPartyId;
    }

    public void setCetPartyId(Integer cetPartyId) {
        this.cetPartyId = cetPartyId;
    }

    public Byte getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Byte projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Boolean getProjectIsDeleted() {
        return projectIsDeleted;
    }

    public void setProjectIsDeleted(Boolean projectIsDeleted) {
        this.projectIsDeleted = projectIsDeleted;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getChooseUserCode() {
        return chooseUserCode;
    }

    public void setChooseUserCode(String chooseUserCode) {
        this.chooseUserCode = chooseUserCode == null ? null : chooseUserCode.trim();
    }

    public String getChooseUserName() {
        return chooseUserName;
    }

    public void setChooseUserName(String chooseUserName) {
        this.chooseUserName = chooseUserName == null ? null : chooseUserName.trim();
    }
}