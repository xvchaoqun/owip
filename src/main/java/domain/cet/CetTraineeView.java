package domain.cet;

import persistence.cet.CetTrainMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;

public class CetTraineeView implements Serializable {

    public CetTrain getCetTrain(){

        if(trainId==null) return null;
        CetTrainMapper cetTrainMapper = CmTag.getBean(CetTrainMapper.class);
        return cetTrainMapper.selectByPrimaryKey(trainId);
    }

    private Integer id;

    private Integer trainId;

    private Integer objId;

    private Boolean isQuit;

    private String remark;

    private Integer userId;

    private Integer traineeTypeId;

    private Integer projectId;

    private Boolean objIsQuit;

    private Integer planId;

    private BigDecimal totalPeriod;

    private BigDecimal finishPeriod;

    private Integer courseCount;

    private Integer finishCount;

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

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Boolean getObjIsQuit() {
        return objIsQuit;
    }

    public void setObjIsQuit(Boolean objIsQuit) {
        this.objIsQuit = objIsQuit;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }
}