package domain.cet;

import domain.sys.SysUserView;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetAnnualRequire implements Serializable {
    
    public SysUserView getOpUser(){
        return CmTag.getUserById(opUserId);
    }
    
    private Integer id;

    private Integer annualId;

    private Integer adminLevel;

    private BigDecimal period;

    private BigDecimal maxSpecialPeriod;

    private BigDecimal maxDailyPeriod;

    private BigDecimal maxPartyPeriod;

    private BigDecimal maxUnitPeriod;

    private BigDecimal maxUpperPeriod;

    private String remark;

    private Integer relateCount;

    private Date opTime;

    private Integer opUserId;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnnualId() {
        return annualId;
    }

    public void setAnnualId(Integer annualId) {
        this.annualId = annualId;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public BigDecimal getMaxSpecialPeriod() {
        return maxSpecialPeriod;
    }

    public void setMaxSpecialPeriod(BigDecimal maxSpecialPeriod) {
        this.maxSpecialPeriod = maxSpecialPeriod;
    }

    public BigDecimal getMaxDailyPeriod() {
        return maxDailyPeriod;
    }

    public void setMaxDailyPeriod(BigDecimal maxDailyPeriod) {
        this.maxDailyPeriod = maxDailyPeriod;
    }

    public BigDecimal getMaxPartyPeriod() {
        return maxPartyPeriod;
    }

    public void setMaxPartyPeriod(BigDecimal maxPartyPeriod) {
        this.maxPartyPeriod = maxPartyPeriod;
    }

    public BigDecimal getMaxUnitPeriod() {
        return maxUnitPeriod;
    }

    public void setMaxUnitPeriod(BigDecimal maxUnitPeriod) {
        this.maxUnitPeriod = maxUnitPeriod;
    }

    public BigDecimal getMaxUpperPeriod() {
        return maxUpperPeriod;
    }

    public void setMaxUpperPeriod(BigDecimal maxUpperPeriod) {
        this.maxUpperPeriod = maxUpperPeriod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRelateCount() {
        return relateCount;
    }

    public void setRelateCount(Integer relateCount) {
        this.relateCount = relateCount;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}