package domain.leader;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;

public class LeaderView implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public CadreView getCadre(){
        return CmTag.getCadreByUserId(userId);
    }

    private Integer id;

    private Integer userId;

    private Integer typeId;

    private String job;

    private Integer sortOrder;

    private Byte cadreStatus;

    private Integer isCommitteeMember;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getCadreStatus() {
        return cadreStatus;
    }

    public void setCadreStatus(Byte cadreStatus) {
        this.cadreStatus = cadreStatus;
    }

    public Integer getIsCommitteeMember() {
        return isCommitteeMember;
    }

    public void setIsCommitteeMember(Integer isCommitteeMember) {
        this.isCommitteeMember = isCommitteeMember;
    }
}