package domain.dr;

import java.io.Serializable;

public class DrOnlinePost implements Serializable {
    private Integer id;

    private Integer unitPostId;

    private String name;

    private Integer onlineId;

    private Boolean hasCandidate;

    private String candidates;

    private Boolean hasCompetitive;

    private Integer competitiveNum;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Boolean getHasCandidate() {
        return hasCandidate;
    }

    public void setHasCandidate(Boolean hasCandidate) {
        this.hasCandidate = hasCandidate;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates == null ? null : candidates.trim();
    }

    public Boolean getHasCompetitive() {
        return hasCompetitive;
    }

    public void setHasCompetitive(Boolean hasCompetitive) {
        this.hasCompetitive = hasCompetitive;
    }

    public Integer getCompetitiveNum() {
        return competitiveNum;
    }

    public void setCompetitiveNum(Integer competitiveNum) {
        this.competitiveNum = competitiveNum;
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
}