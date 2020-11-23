package domain.cet;

import persistence.cet.CetPartyMapper;
import persistence.cet.CetProjectObjMapper;
import persistence.cet.CetTrainMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetTraineeView implements Serializable {

    public CetTrain getCetTrain(){

        if(trainId==null) return null;
        CetTrainMapper cetTrainMapper = CmTag.getBean(CetTrainMapper.class);
        return cetTrainMapper.selectByPrimaryKey(trainId);
    }

    public CetProjectObj getObj(){

        if(objId==null) return null;
        CetProjectObjMapper cetProjectObjMapper = CmTag.getBean(CetProjectObjMapper.class);
        return cetProjectObjMapper.selectByPrimaryKey(objId);
    }

    public CetParty getCetParty(){

        if(cetPartyId==null) return null;
        return CmTag.getBean(CetPartyMapper.class).selectByPrimaryKey(cetPartyId);
    }

    private Integer objId;

    private Integer userId;

    private Integer traineeTypeId;

    private Integer projectId;

    private Integer planId;

    private Integer trainId;

    private Boolean objIsQuit;

    private String projectName;

    private Byte projectType;

    private Boolean isPartyProject;

    private Date startDate;

    private Date endDate;

    private Integer cetPartyId;

    private Byte projectStatus;

    private Boolean projectIsDeleted;

    private Long courseCount;

    private BigDecimal finishCount;

    private BigDecimal totalPeriod;

    private BigDecimal finishPeriod;

    private BigDecimal onlineFinishPeriod;

    private static final long serialVersionUID = 1L;

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

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Boolean getObjIsQuit() {
        return objIsQuit;
    }

    public void setObjIsQuit(Boolean objIsQuit) {
        this.objIsQuit = objIsQuit;
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

    public Long getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Long courseCount) {
        this.courseCount = courseCount;
    }

    public BigDecimal getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(BigDecimal finishCount) {
        this.finishCount = finishCount;
    }

    public BigDecimal getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(BigDecimal totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
    }

    public BigDecimal getOnlineFinishPeriod() {
        return onlineFinishPeriod;
    }

    public void setOnlineFinishPeriod(BigDecimal onlineFinishPeriod) {
        this.onlineFinishPeriod = onlineFinishPeriod;
    }
}