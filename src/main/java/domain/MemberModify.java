package domain;

import java.io.Serializable;
import java.util.Date;

public class MemberModify implements Serializable {
    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Byte politicalStatus;

    private Byte type;

    private Byte status;

    private Byte source;

    private Date transferTime;

    private Date applyTime;

    private Date activeTime;

    private Date candidateTime;

    private Date growTime;

    private Date positiveTime;

    private String partyPost;

    private String partyReward;

    private String otherReward;

    private String reason;

    private Integer opUserId;

    private Date opTime;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Byte getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Byte politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getCandidateTime() {
        return candidateTime;
    }

    public void setCandidateTime(Date candidateTime) {
        this.candidateTime = candidateTime;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(Date positiveTime) {
        this.positiveTime = positiveTime;
    }

    public String getPartyPost() {
        return partyPost;
    }

    public void setPartyPost(String partyPost) {
        this.partyPost = partyPost == null ? null : partyPost.trim();
    }

    public String getPartyReward() {
        return partyReward;
    }

    public void setPartyReward(String partyReward) {
        this.partyReward = partyReward == null ? null : partyReward.trim();
    }

    public String getOtherReward() {
        return otherReward;
    }

    public void setOtherReward(String otherReward) {
        this.otherReward = otherReward == null ? null : otherReward.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}