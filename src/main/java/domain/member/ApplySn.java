package domain.member;

import domain.sys.SysUserView;
import service.member.ApplySnRangeService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class ApplySn implements Serializable {

    public SysUserView getUser(){

        return CmTag.getUserById(userId);
    }

    public ApplySnRange getApplySnRange(){

        ApplySnRangeService applySnRangeService = CmTag.getBean(ApplySnRangeService.class);
        return applySnRangeService.get(rangeId);
    }

    private Integer id;

    private Integer year;

    private Long sn;

    private String displaySn;

    private Integer rangeId;

    private Boolean isUsed;

    private Boolean isAbolished;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private Date assignTime;

    private Integer drawUserId;

    private Date drawTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public String getDisplaySn() {
        return displaySn;
    }

    public void setDisplaySn(String displaySn) {
        this.displaySn = displaySn == null ? null : displaySn.trim();
    }

    public Integer getRangeId() {
        return rangeId;
    }

    public void setRangeId(Integer rangeId) {
        this.rangeId = rangeId;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Boolean getIsAbolished() {
        return isAbolished;
    }

    public void setIsAbolished(Boolean isAbolished) {
        this.isAbolished = isAbolished;
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

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getDrawUserId() {
        return drawUserId;
    }

    public void setDrawUserId(Integer drawUserId) {
        this.drawUserId = drawUserId;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }
}