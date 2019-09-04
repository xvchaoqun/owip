package domain.ps;

import domain.sys.SysUserView;
import service.ps.PsInfoService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class PsInfoView implements Serializable {

    public Long getCountNumber(){
        PsInfoService psInfoService = CmTag.getBean(PsInfoService.class);
        return psInfoService.getAllCountNumberById(id);
    }

    public SysUserView getRectorUser(){
        return CmTag.getUserById(rectorUserId);
    }

    public SysUserView getAdminUser(){return CmTag.getUserById(adminUserId);}

    private Integer id;

    private String seq;

    private String name;

    private Date foundDate;

    private Date abolishDate;

    private Integer sortOrder;

    private Boolean isHistory;

    private String remark;

    private Boolean isDeleted;

    private Integer rectorUserId;

    private String rectorTitle;

    private String rectorMobile;

    private String jointIds;

    private Integer hostId;

    private Integer adminUserId;

    private String adminMobile;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Date getAbolishDate() {
        return abolishDate;
    }

    public void setAbolishDate(Date abolishDate) {
        this.abolishDate = abolishDate;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Boolean isHistory) {
        this.isHistory = isHistory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getRectorUserId() {
        return rectorUserId;
    }

    public void setRectorUserId(Integer rectorUserId) {
        this.rectorUserId = rectorUserId;
    }

    public String getRectorTitle() {
        return rectorTitle;
    }

    public void setRectorTitle(String rectorTitle) {
        this.rectorTitle = rectorTitle == null ? null : rectorTitle.trim();
    }

    public String getRectorMobile() {
        return rectorMobile;
    }

    public void setRectorMobile(String rectorMobile) {
        this.rectorMobile = rectorMobile == null ? null : rectorMobile.trim();
    }

    public String getJointIds() {
        return jointIds;
    }

    public void setJointIds(String jointIds) {
        this.jointIds = jointIds == null ? null : jointIds.trim();
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile == null ? null : adminMobile.trim();
    }
}