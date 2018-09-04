package domain.sc.scMatter;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScMatterAccess implements Serializable {

    public SysUserView getReturnUser(){
        return CmTag.getUserById(returnUserId);
    }
    public SysUserView getHandleUser(){
        return CmTag.getUserById(handleUserId);
    }
    private Integer id;

    private Integer year;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date accessDate;

    private String accessFile;

    private Integer unitId;

    private Boolean isCopy;

    private String purpose;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date handleDate;

    private Integer handleUserId;

    private String receiver;

    private String receivePdf;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date returnDate;

    private Integer returnUserId;

    private String remark;

    private Boolean isDeleted;

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

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public String getAccessFile() {
        return accessFile;
    }

    public void setAccessFile(String accessFile) {
        this.accessFile = accessFile == null ? null : accessFile.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Boolean getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Boolean isCopy) {
        this.isCopy = isCopy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getReceivePdf() {
        return receivePdf;
    }

    public void setReceivePdf(String receivePdf) {
        this.receivePdf = receivePdf == null ? null : receivePdf.trim();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getReturnUserId() {
        return returnUserId;
    }

    public void setReturnUserId(Integer returnUserId) {
        this.returnUserId = returnUserId;
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
}