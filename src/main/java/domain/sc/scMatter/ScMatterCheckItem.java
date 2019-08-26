package domain.sc.scMatter;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScMatterCheckItem implements Serializable {
    private Integer id;

    private Integer checkId;

    private Integer userId;

    private Integer recordId;

    private String recordIds;

    private Integer recordUserId;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date compareDate;

    private String resultType;

    private String selfFile;

    private Byte confirmType;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date confirmDate;

    private String checkReason;

    private String handleType;

    private String checkFile;

    private String owHandleType;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date owHandleDate;

    private String owHandleFile;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date owAffectDate;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds == null ? null : recordIds.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Date getCompareDate() {
        return compareDate;
    }

    public void setCompareDate(Date compareDate) {
        this.compareDate = compareDate;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType == null ? null : resultType.trim();
    }

    public String getSelfFile() {
        return selfFile;
    }

    public void setSelfFile(String selfFile) {
        this.selfFile = selfFile == null ? null : selfFile.trim();
    }

    public Byte getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Byte confirmType) {
        this.confirmType = confirmType;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason == null ? null : checkReason.trim();
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType == null ? null : handleType.trim();
    }

    public String getCheckFile() {
        return checkFile;
    }

    public void setCheckFile(String checkFile) {
        this.checkFile = checkFile == null ? null : checkFile.trim();
    }

    public String getOwHandleType() {
        return owHandleType;
    }

    public void setOwHandleType(String owHandleType) {
        this.owHandleType = owHandleType == null ? null : owHandleType.trim();
    }

    public Date getOwHandleDate() {
        return owHandleDate;
    }

    public void setOwHandleDate(Date owHandleDate) {
        this.owHandleDate = owHandleDate;
    }

    public String getOwHandleFile() {
        return owHandleFile;
    }

    public void setOwHandleFile(String owHandleFile) {
        this.owHandleFile = owHandleFile == null ? null : owHandleFile.trim();
    }

    public Date getOwAffectDate() {
        return owAffectDate;
    }

    public void setOwAffectDate(Date owAffectDate) {
        this.owAffectDate = owAffectDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}