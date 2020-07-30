package domain.cet;

import persistence.cet.CetProjectObjMapper;
import persistence.cet.CetTrainCourseMapper;
import persistence.cet.common.ICetMapper;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetTrainView implements Serializable {

    public int getCourseNum(){

        CetTrainCourseMapper trainCourseMapper = CmTag.getBean(CetTrainCourseMapper.class);
        CetTrainCourseExample example = new CetTrainCourseExample();
        example.createCriteria().andTrainIdEqualTo(id);

        return (int)trainCourseMapper.countByExample(example);
    }

    public Integer getObjCount(){

        if(projectId==null) return null;

        CetProjectObjMapper cetProjectObjMapper = CmTag.getBean(CetProjectObjMapper.class);
        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return (int)cetProjectObjMapper.countByExample(example);
    }

    public Boolean getAutoSwitch() {
        return enrollStatus == CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT;
    }

    public Byte getSwitchStatus() {

        return BaseCetTrain.getSwitchStatus(enrollStatus, startTime, endTime);
    }

    private Integer id;

    private Integer planId;

    private Boolean isOnCampus;

    private String name;

    private Boolean hasSummary;

    private String summary;

    private Date startDate;

    private Date endDate;

    private Date startTime;

    private Date endTime;

    private Byte enrollStatus;

    private Integer evaCount;

    private Boolean evaAnonymous;

    private String evaNote;

    private Boolean evaClosed;

    private Date evaCloseTime;

    private String remark;

    private Boolean isDeleted;

    private Boolean isFinished;

    private Date createTime;

    private Integer year;

    private Integer projectId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Boolean getIsOnCampus() {
        return isOnCampus;
    }

    public void setIsOnCampus(Boolean isOnCampus) {
        this.isOnCampus = isOnCampus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(Boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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

    public Integer getEvaCount() {
        return evaCount;
    }

    public void setEvaCount(Integer evaCount) {
        this.evaCount = evaCount;
    }

    public Boolean getEvaAnonymous() {
        return evaAnonymous;
    }

    public void setEvaAnonymous(Boolean evaAnonymous) {
        this.evaAnonymous = evaAnonymous;
    }

    public String getEvaNote() {
        return evaNote;
    }

    public void setEvaNote(String evaNote) {
        this.evaNote = evaNote == null ? null : evaNote.trim();
    }

    public Boolean getEvaClosed() {
        return evaClosed;
    }

    public void setEvaClosed(Boolean evaClosed) {
        this.evaClosed = evaClosed;
    }

    public Date getEvaCloseTime() {
        return evaCloseTime;
    }

    public void setEvaCloseTime(Date evaCloseTime) {
        this.evaCloseTime = evaCloseTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}