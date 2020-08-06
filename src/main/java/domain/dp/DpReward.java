package domain.dp;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class DpReward implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer rewardLevel;

    private Date rewardTime;

    private String name;

    private String unit;

    @SignRes
    private String proof;

    private String proofFilename;

    private Boolean isIndependent;

    private Integer rank;

    private Integer sortOrder;

    private Byte status;

    private Integer rewardType;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(Integer rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof == null ? null : proof.trim();
    }

    public String getProofFilename() {
        return proofFilename;
    }

    public void setProofFilename(String proofFilename) {
        this.proofFilename = proofFilename == null ? null : proofFilename.trim();
    }

    public Boolean getIsIndependent() {
        return isIndependent;
    }

    public void setIsIndependent(Boolean isIndependent) {
        this.isIndependent = isIndependent;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}