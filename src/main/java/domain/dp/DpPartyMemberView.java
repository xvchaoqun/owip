package domain.dp;

import domain.sys.SysUserView;
import sys.helper.DpPartyHelper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class DpPartyMemberView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public DpParty getDpParty(){
        return DpPartyHelper.getDpPartyByGroupId(groupId);
    }

    private Integer id;

    private Integer groupId;

    private Integer userId;

    private String typeIds;

    private Integer postId;

    private Date assignDate;

    private String officePhone;

    private String mobile;

    private Boolean isAdmin;

    private Integer sortOrder;

    private Boolean presentMember;

    private Date deleteTime;

    private Integer groupPartyId;

    private Boolean isPresent;

    private Boolean isDeleted;

    private Integer unitId;

    private Boolean isDpPartyDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(String typeIds) {
        this.typeIds = typeIds == null ? null : typeIds.trim();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone == null ? null : officePhone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getPresentMember() {
        return presentMember;
    }

    public void setPresentMember(Boolean presentMember) {
        this.presentMember = presentMember;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getGroupPartyId() {
        return groupPartyId;
    }

    public void setGroupPartyId(Integer groupPartyId) {
        this.groupPartyId = groupPartyId;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Boolean getIsDpPartyDeleted() {
        return isDpPartyDeleted;
    }

    public void setIsDpPartyDeleted(Boolean isDpPartyDeleted) {
        this.isDpPartyDeleted = isDpPartyDeleted;
    }
}