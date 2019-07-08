package domain.abroad;

import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.constants.AbroadConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PassportApply implements Serializable {

    private CadreView cadreView;
    private SysUserView sysUserView;

    public SysUserView getApplyUser(){

        if(sysUserView==null) {
            CadreView cadre = getCadre();
            sysUserView = CmTag.getUserById(cadre.getUserId());
        }

        return sysUserView;
    }
    public CadreView getCadre(){

        if(cadreView==null){
            cadreView = CmTag.getCadreById(cadreId);
        }

        return cadreView;
    }

    public SysUserView getApprovalUser(){
        return CmTag.getUserById(userId);
    }

    public SysUserView getOpUser(){
        return CmTag.getUserById(opUserId);
    }
    public MetaType getPassportClass(){

        Map<Integer, MetaType> passportClassMap = CmTag.getMetaTypes("mc_passport_type");
        return passportClassMap.get(classId);
    }
    public String getStatusName(){

        return AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_MAP.get(status);
    }

    private Integer id;

    private Integer cadreId;

    private Integer classId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date applyDate;

    private Byte status;

    private Boolean abolish;

    private Integer userId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date approveTime;

    private Integer opUserId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date expectDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date handleDate;

    private Integer handleUserId;

    private String remark;

    private Date createTime;

    private String ip;

    private Boolean isDeleted;

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

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
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

    public Integer getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(Integer handleUserId) {
        this.handleUserId = handleUserId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}