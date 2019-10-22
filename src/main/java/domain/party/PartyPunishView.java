package domain.party;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PartyPunishView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Byte type;

    private Integer partyId;

    private Integer branchId;

    private Integer userId;

    private Date punishTime;

    private Date endTime;

    private String name;

    private String unit;

    private Integer sortOrder;

    private String remark;

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

    public Date getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(Date punishTime) {
        this.punishTime = punishTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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