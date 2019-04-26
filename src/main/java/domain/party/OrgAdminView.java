package domain.party;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class OrgAdminView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private Byte status;

    private String remark;

    private Date createTime;

    private Integer partySortOrder;

    private Integer branchPartyId;

    private Integer branchPartySortOrder;

    private Integer branchSortOrder;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public Integer getBranchPartyId() {
        return branchPartyId;
    }

    public void setBranchPartyId(Integer branchPartyId) {
        this.branchPartyId = branchPartyId;
    }

    public Integer getBranchPartySortOrder() {
        return branchPartySortOrder;
    }

    public void setBranchPartySortOrder(Integer branchPartySortOrder) {
        this.branchPartySortOrder = branchPartySortOrder;
    }

    public Integer getBranchSortOrder() {
        return branchSortOrder;
    }

    public void setBranchSortOrder(Integer branchSortOrder) {
        this.branchSortOrder = branchSortOrder;
    }
}