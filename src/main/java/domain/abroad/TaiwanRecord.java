package domain.abroad;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class TaiwanRecord implements Serializable {

    private CadreView cadreView;
    private SysUserView sysUserView;

    public SysUserView getUser(){

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

    private Integer id;

    private Integer cadreId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String reason;

    private String passportCode;

    private Byte handleType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date handleDate;

    private Integer handleUserId;

    private String remark;

    private Date createTime;

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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getPassportCode() {
        return passportCode;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode == null ? null : passportCode.trim();
    }

    public Byte getHandleType() {
        return handleType;
    }

    public void setHandleType(Byte handleType) {
        this.handleType = handleType;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}