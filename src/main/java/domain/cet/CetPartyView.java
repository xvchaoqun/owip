package domain.cet;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class CetPartyView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer partyId;

    private Integer userId;

    private String partyCode;

    private String partyName;

    private Boolean partyIsDeleted;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode == null ? null : partyCode.trim();
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public Boolean getPartyIsDeleted() {
        return partyIsDeleted;
    }

    public void setPartyIsDeleted(Boolean partyIsDeleted) {
        this.partyIsDeleted = partyIsDeleted;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}