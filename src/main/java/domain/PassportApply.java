package domain;

import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PassportApply implements Serializable {

    public Cadre getCadre(){
        return CmTag.getCadreById(cadreId);
    }
    public SysUser getApplyUser(){
        Cadre cadre = getCadre();
        return CmTag.getUserById(cadre.getUserId());
    }
    public SysUser getApprovalUser(){
        if(userId!=null)
            return CmTag.getUserById(userId);
        return null;
    }
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    public String getStatusName(){

        return SystemConstants.PASSPORT_APPLY_STATUS_MAP.get(status);
    }
    private Integer id;

    private Integer cadreId;

    private Integer classId;

    private Date applyDate;

    private Byte status;

    private Boolean abolish;

    private Integer userId;

    private Date approveTime;

    private Date expectDate;

    private Date handleDate;

    private String remark;

    private Date createTime;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getAbolish() {
        return abolish;
    }

    public void setAbolish(Boolean abolish) {
        this.abolish = abolish;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}