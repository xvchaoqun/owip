package domain.sc.scCommittee;

import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScCommitteeView implements Serializable {

    public String getCode(){
        return String.format("党委常委会〔%s〕号", DateUtils.formatDate(holdDate, "yyyyMMdd"));
    }

    private Integer id;

    private String filePath;

    private Integer year;

    private Date holdDate;

    private Integer topicNum;

    private Integer committeeMemberCount;

    private String logFile;

    private String pptFile;

    private String attendUsers;

    private String remark;

    private Boolean isDeleted;

    private Integer count;

    private Integer absentCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }

    public Integer getCommitteeMemberCount() {
        return committeeMemberCount;
    }

    public void setCommitteeMemberCount(Integer committeeMemberCount) {
        this.committeeMemberCount = committeeMemberCount;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public String getPptFile() {
        return pptFile;
    }

    public void setPptFile(String pptFile) {
        this.pptFile = pptFile == null ? null : pptFile.trim();
    }

    public String getAttendUsers() {
        return attendUsers;
    }

    public void setAttendUsers(String attendUsers) {
        this.attendUsers = attendUsers == null ? null : attendUsers.trim();
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }
}