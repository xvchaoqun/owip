package domain.pcs;

import java.io.Serializable;

public class PcsVoteCandidate implements Serializable {
    private Integer id;

    private Integer groupId;

    private Integer userId;

    private Boolean isFromStage;

    private String realname;

    private Integer agree;

    private Integer degree;

    private Integer abstain;

    private Integer sortOrder;

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

    public Boolean getIsFromStage() {
        return isFromStage;
    }

    public void setIsFromStage(Boolean isFromStage) {
        this.isFromStage = isFromStage;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getAbstain() {
        return abstain;
    }

    public void setAbstain(Integer abstain) {
        this.abstain = abstain;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}