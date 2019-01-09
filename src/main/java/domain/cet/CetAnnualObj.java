package domain.cet;

import domain.sys.SysUserView;
import service.cet.CetAnnualObjService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CetAnnualObj implements Serializable {
    
    public Map<String, BigDecimal> getR(){
        
        if(hasArchived) return null;
        
        CetAnnualObjService cetAnnualObjService = CmTag.getBean(CetAnnualObjService.class);
        return cetAnnualObjService.getFinishPeriodMap(userId, year);
    }
    
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    
    private Integer id;

    private Integer year;

    private Integer annualId;

    private Integer userId;

    private String title;

    private Integer adminLevel;

    private Boolean needUpdateRequire;

    private Integer postType;

    private Date lpWorkTime;

    private BigDecimal period;

    private BigDecimal finishPeriod;

    private BigDecimal maxSpecialPeriod;

    private BigDecimal maxDailyPeriod;

    private BigDecimal maxPartyPeriod;

    private BigDecimal maxUnitPeriod;

    private BigDecimal maxUpperPeriod;

    private BigDecimal specialPeriod;

    private BigDecimal dailyPeriod;

    private BigDecimal partyPeriod;

    private BigDecimal unitPeriod;

    private BigDecimal upperPeriod;

    private Boolean hasArchived;

    private Boolean isQuit;

    private Integer sortOrder;

    private String remark;

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

    public Integer getAnnualId() {
        return annualId;
    }

    public void setAnnualId(Integer annualId) {
        this.annualId = annualId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Boolean getNeedUpdateRequire() {
        return needUpdateRequire;
    }

    public void setNeedUpdateRequire(Boolean needUpdateRequire) {
        this.needUpdateRequire = needUpdateRequire;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
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

    public BigDecimal getSpecialPeriod() {
        return specialPeriod;
    }

    public void setSpecialPeriod(BigDecimal specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public BigDecimal getDailyPeriod() {
        return dailyPeriod;
    }

    public void setDailyPeriod(BigDecimal dailyPeriod) {
        this.dailyPeriod = dailyPeriod;
    }

    public BigDecimal getPartyPeriod() {
        return partyPeriod;
    }

    public void setPartyPeriod(BigDecimal partyPeriod) {
        this.partyPeriod = partyPeriod;
    }

    public BigDecimal getUnitPeriod() {
        return unitPeriod;
    }

    public void setUnitPeriod(BigDecimal unitPeriod) {
        this.unitPeriod = unitPeriod;
    }

    public BigDecimal getUpperPeriod() {
        return upperPeriod;
    }

    public void setUpperPeriod(BigDecimal upperPeriod) {
        this.upperPeriod = upperPeriod;
    }

    public Boolean getHasArchived() {
        return hasArchived;
    }

    public void setHasArchived(Boolean hasArchived) {
        this.hasArchived = hasArchived;
    }

    public Boolean getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Boolean isQuit) {
        this.isQuit = isQuit;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}