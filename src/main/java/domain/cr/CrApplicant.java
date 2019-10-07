package domain.cr;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CrApplicant implements Serializable {

    public CadreView getCadre(){
        return CmTag.getCadreByUserId(userId);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Integer infoId;

    private Integer firstPostId;

    private Integer secondPostId;

    private String eva;

    private String reason;

    private Date enrollTime;

    private Byte firstCheckStatus;

    private String firstCheckRemark;

    private Byte secondCheckStatus;

    private String secondCheckRemark;

    private Integer sortOrder;

    private Boolean hasSubmit;

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

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getFirstPostId() {
        return firstPostId;
    }

    public void setFirstPostId(Integer firstPostId) {
        this.firstPostId = firstPostId;
    }

    public Integer getSecondPostId() {
        return secondPostId;
    }

    public void setSecondPostId(Integer secondPostId) {
        this.secondPostId = secondPostId;
    }

    public String getEva() {
        return eva;
    }

    public void setEva(String eva) {
        this.eva = eva == null ? null : eva.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Byte getFirstCheckStatus() {
        return firstCheckStatus;
    }

    public void setFirstCheckStatus(Byte firstCheckStatus) {
        this.firstCheckStatus = firstCheckStatus;
    }

    public String getFirstCheckRemark() {
        return firstCheckRemark;
    }

    public void setFirstCheckRemark(String firstCheckRemark) {
        this.firstCheckRemark = firstCheckRemark == null ? null : firstCheckRemark.trim();
    }

    public Byte getSecondCheckStatus() {
        return secondCheckStatus;
    }

    public void setSecondCheckStatus(Byte secondCheckStatus) {
        this.secondCheckStatus = secondCheckStatus;
    }

    public String getSecondCheckRemark() {
        return secondCheckRemark;
    }

    public void setSecondCheckRemark(String secondCheckRemark) {
        this.secondCheckRemark = secondCheckRemark == null ? null : secondCheckRemark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getHasSubmit() {
        return hasSubmit;
    }

    public void setHasSubmit(Boolean hasSubmit) {
        this.hasSubmit = hasSubmit;
    }
}