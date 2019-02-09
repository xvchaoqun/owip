package domain.sc.scCommittee;

import java.io.Serializable;

public class ScCommitteeTopic implements Serializable {
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

    private Boolean isDeleted;

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}