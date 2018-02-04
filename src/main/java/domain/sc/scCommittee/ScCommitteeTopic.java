package domain.sc.scCommittee;

import java.io.Serializable;

public class ScCommitteeTopic implements Serializable {
    private Integer id;

    private Integer committeeId;

    private String name;

    private String content;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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