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

    private Integer userId;

    private Integer traineeTypeId;

    private Integer projectId;

    private Integer planId;

    private Integer trainId;

    private Boolean objIsQuit;

    private Long courseCount;

    private BigDecimal finishCount;

    private BigDecimal totalPeriod;

    private BigDecimal finishPeriod;

    private static final long serialVersionUID = 1L;

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
}