package domain.cet;

import domain.base.MetaType;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetTrainView implements Serializable {

    public String getSn(){

        if(type==null) return null;
        MetaType metaType = CmTag.getMetaType(type);
        if(metaType==null) return null;

        return String.format("%s[%s]%s号",
                metaType.getName(),
                year, num);
    }

    public Boolean getAutoSwitch(){
        return enrollStatus == CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT;
    }

    public Byte getSwitchStatus() {

        // 手动开关判断
        if (enrollStatus != CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT) {
            return enrollStatus;
        }

        // 自动开关判断
        Date now = new Date();
        if (startTime != null && endTime != null) {

            if (now.after(startTime) && now.before(endTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (startTime != null) {

            if (now.after(startTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        } else if (endTime != null) {

            if (now.before(endTime)) {
                return CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN;
            }
        }

        return CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED;
    }

    private Integer id;

    private Integer year;

    private Integer type;

    private Integer num;

    private String name;

    private Boolean hasSummary;

    private Integer templateId;

    private Date startDate;

    private Date endDate;

    private Date startTime;

    private Date endTime;

    private Byte enrollStatus;

    private Byte pubStatus;

    private String remark;

    private Boolean isDeleted;

    private Boolean isFinished;

    private Date createTime;

    private String traineeTypes;

    private String summary;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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

    public Byte getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(Byte pubStatus) {
        this.pubStatus = pubStatus;
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

    public String getTraineeTypes() {
        return traineeTypes;
    }

    public void setTraineeTypes(String traineeTypes) {
        this.traineeTypes = traineeTypes == null ? null : traineeTypes.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}