package domain.verify;

import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class VerifyGrowTime implements Serializable {

    public CadreView getCadre(){
        return CmTag.getCadreById(cadreId);
    }

    public SysUserView getSubmitUser(){
        return CmTag.getUserById(submitUserId);
    }

    public SysUserView getUpdateUser(){
        return CmTag.getUserById(updateUserId);
    }

    private Integer id;

    private Integer cadreId;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date oldGrowTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date verifyGrowTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date materialTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date materialGrowTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date adTime;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date adGrowTime;

    private String remark;

    private Byte status;

    private Integer submitUserId;

    private String submitIp;

    private Date submitTime;

    private Integer updateUserId;

    private String updateIp;

    private Date updateTime;

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

    public Date getOldGrowTime() {
        return oldGrowTime;
    }

    public void setOldGrowTime(Date oldGrowTime) {
        this.oldGrowTime = oldGrowTime;
    }

    public Date getVerifyGrowTime() {
        return verifyGrowTime;
    }

    public void setVerifyGrowTime(Date verifyGrowTime) {
        this.verifyGrowTime = verifyGrowTime;
    }

    public Date getMaterialTime() {
        return materialTime;
    }

    public void setMaterialTime(Date materialTime) {
        this.materialTime = materialTime;
    }

    public Date getMaterialGrowTime() {
        return materialGrowTime;
    }

    public void setMaterialGrowTime(Date materialGrowTime) {
        this.materialGrowTime = materialGrowTime;
    }

    public Date getAdTime() {
        return adTime;
    }

    public void setAdTime(Date adTime) {
        this.adTime = adTime;
    }

    public Date getAdGrowTime() {
        return adGrowTime;
    }

    public void setAdGrowTime(Date adGrowTime) {
        this.adGrowTime = adGrowTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(Integer submitUserId) {
        this.submitUserId = submitUserId;
    }

    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp == null ? null : submitIp.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}