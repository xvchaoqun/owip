package domain.party;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PartyRewardView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Byte type;

    private Integer partyId;

    private Integer branchId;

    private Integer userId;

    private Date rewardTime;

    private Integer rewardType;

    private String name;

    private String unit;

    private String proof;

    private String proofFilename;

    private String remark;

    private Integer sortOrder;

    private Integer partySortOrder;

    private Integer branchSortOrder;

    private Integer branchPartyId;

    private Integer userPartyId;

    private Integer userBranchId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public Integer getBranchSortOrder() {
        return branchSortOrder;
    }

    public void setBranchSortOrder(Integer branchSortOrder) {
        this.branchSortOrder = branchSortOrder;
    }

    public Integer getBranchPartyId() {
        return branchPartyId;
    }

    public void setBranchPartyId(Integer branchPartyId) {
        this.branchPartyId = branchPartyId;
    }

    public Integer getUserPartyId() {
        return userPartyId;
    }

    public void setUserPartyId(Integer userPartyId) {
        this.userPartyId = userPartyId;
    }

    public Integer getUserBranchId() {
        return userBranchId;
    }

    public void setUserBranchId(Integer userBranchId) {
        this.userBranchId = userBranchId;
    }
}