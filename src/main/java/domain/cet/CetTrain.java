package domain.cet;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class CetTrain implements Serializable {

     // 判断选课是否正在进行
    public boolean getIsApplyOpen() {

        Date now = new Date();
        if (startTime != null && endTime != null) {

            return now.after(startTime) && now.before(endTime);

        } else if (startTime != null) {

            return startTime.before(now);

        } else if (endTime != null) {

            return now.before(endTime);

        }

        return false;
    }

    private Integer id;

    private Integer planId;

    private Boolean isOnCampus;

    private String name;

    private Boolean hasSummary;

    private String summary;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date endDate;

    private Date startTime;

    private Date endTime;

    private Integer courseNum;

    private Integer evaCount;

    private Boolean evaAnonymous;

    private String evaNote;

    private Boolean evaClosed;

    private Date evaCloseTime;

    private String remark;

    private Boolean isDeleted;

    private Date createTime;

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

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}