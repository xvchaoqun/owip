package domain.sc.scCommittee;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScCommitteeTopicView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    private Integer id;

    private Integer committeeId;

    private Integer seq;

    private String name;

    private String unitIds;

    private String content;

    private String memo;

    private Boolean hasVote;

    private Boolean hasOtherVote;

    private String voteFilePath;

    private String remark;

    private Integer recordUserId;

    private Boolean isDeleted;

    private Integer year;

    private Date holdDate;

    private Integer committeeMemberCount;

    private BigDecimal count;

    private BigDecimal absentCount;

    private String attendUsers;

    private String filePath;

    private String logFile;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Integer committeeId) {
        this.committeeId = committeeId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds == null ? null : unitIds.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Boolean getHasVote() {
        return hasVote;
    }

    public void setHasVote(Boolean hasVote) {
        this.hasVote = hasVote;
    }

    public Boolean getHasOtherVote() {
        return hasOtherVote;
    }

    public void setHasOtherVote(Boolean hasOtherVote) {
        this.hasOtherVote = hasOtherVote;
    }

    public String getVoteFilePath() {
        return voteFilePath;
    }

    public void setVoteFilePath(String voteFilePath) {
        this.voteFilePath = voteFilePath == null ? null : voteFilePath.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public Integer getCommitteeMemberCount() {
        return committeeMemberCount;
    }

    public void setCommitteeMemberCount(Integer committeeMemberCount) {
        this.committeeMemberCount = committeeMemberCount;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(BigDecimal absentCount) {
        this.absentCount = absentCount;
    }

    public String getAttendUsers() {
        return attendUsers;
    }

    public void setAttendUsers(String attendUsers) {
        this.attendUsers = attendUsers == null ? null : attendUsers.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }
}