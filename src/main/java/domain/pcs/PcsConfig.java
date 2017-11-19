package domain.pcs;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class PcsConfig implements Serializable {
    private Integer id;

    private String name;

    private Boolean isCurrent;

    private Date createTime;

    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date proposalSubmitTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date proposalSupportTime;

    private Integer proposalSupportCount;

    private Integer committeeJoinCount;

    private Integer dwSendVote;

    private Integer jwSendVote;

    private Integer dwBackVote;

    private Integer jwBackVote;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getProposalSubmitTime() {
        return proposalSubmitTime;
    }

    public void setProposalSubmitTime(Date proposalSubmitTime) {
        this.proposalSubmitTime = proposalSubmitTime;
    }

    public Date getProposalSupportTime() {
        return proposalSupportTime;
    }

    public void setProposalSupportTime(Date proposalSupportTime) {
        this.proposalSupportTime = proposalSupportTime;
    }

    public Integer getProposalSupportCount() {
        return proposalSupportCount;
    }

    public void setProposalSupportCount(Integer proposalSupportCount) {
        this.proposalSupportCount = proposalSupportCount;
    }

    public Integer getCommitteeJoinCount() {
        return committeeJoinCount;
    }

    public void setCommitteeJoinCount(Integer committeeJoinCount) {
        this.committeeJoinCount = committeeJoinCount;
    }

    public Integer getDwSendVote() {
        return dwSendVote;
    }

    public void setDwSendVote(Integer dwSendVote) {
        this.dwSendVote = dwSendVote;
    }

    public Integer getJwSendVote() {
        return jwSendVote;
    }

    public void setJwSendVote(Integer jwSendVote) {
        this.jwSendVote = jwSendVote;
    }

    public Integer getDwBackVote() {
        return dwBackVote;
    }

    public void setDwBackVote(Integer dwBackVote) {
        this.dwBackVote = dwBackVote;
    }

    public Integer getJwBackVote() {
        return jwBackVote;
    }

    public void setJwBackVote(Integer jwBackVote) {
        this.jwBackVote = jwBackVote;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}