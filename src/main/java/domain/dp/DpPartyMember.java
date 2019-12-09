package domain.dp;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.helper.DpPartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class DpPartyMember implements Serializable {
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

    @DateTimeFormat(pattern = DateUtils.YYYYMM)
    private Date assignDate;

    private String officePhone;

    private Boolean isAdmin;

    private Integer sortOrder;

    private Boolean presentMember;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date deleteTime;

    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}