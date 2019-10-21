package domain.qy;

import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class QyRewardObjView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public Party getParty(){
        return CmTag.getParty(partyId);

    }
    public Branch getBranch(){
        return CmTag.getBranch(branchId);

    }
    private Integer id;

    private Integer recordId;

    private String partyName;

    private Integer partyId;

    private String branchName;

    private Integer branchId;

    private Integer userId;

    private String meetingName;

    private Integer sortOrder;

    private Integer rewardId;

    private Integer rewardSortOrder;

    private Integer year;

    private String rewardName;

    private Byte rewardType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
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

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName == null ? null : meetingName.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public Integer getRewardSortOrder() {
        return rewardSortOrder;
    }

    public void setRewardSortOrder(Integer rewardSortOrder) {
        this.rewardSortOrder = rewardSortOrder;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName == null ? null : rewardName.trim();
    }

    public Byte getRewardType() {
        return rewardType;
    }

    public void setRewardType(Byte rewardType) {
        this.rewardType = rewardType;
    }
}