package domain.cet;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class CetPartySchoolView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer partySchoolId;

    private Integer userId;

    private String partySchoolName;

    private Boolean partySchoolIsHistory;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartySchoolId() {
        return partySchoolId;
    }

    public void setPartySchoolId(Integer partySchoolId) {
        this.partySchoolId = partySchoolId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPartySchoolName() {
        return partySchoolName;
    }

    public void setPartySchoolName(String partySchoolName) {
        this.partySchoolName = partySchoolName == null ? null : partySchoolName.trim();
    }

    public Boolean getPartySchoolIsHistory() {
        return partySchoolIsHistory;
    }

    public void setPartySchoolIsHistory(Boolean partySchoolIsHistory) {
        this.partySchoolIsHistory = partySchoolIsHistory;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}