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
        return cetAnnualObjService.getFinishPeriodMap(this);
    }

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    private Integer id;

    private Integer year;

    private Integer traineeTypeId;

    private Integer annualId;

    private Integer userId;

    private String title;

    private Integer adminLevel;

    private Boolean needUpdateRequire;

    private Integer postType;

    private String identity;

    private Date lpWorkTime;

    private BigDecimal periodOffline;

    private BigDecimal periodOnline;

    private BigDecimal finishPeriodOffline;

    private BigDecimal finishPeriodOnline;

    private BigDecimal specialPeriod;

    private BigDecimal dailyPeriod;

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

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public Date getLpWorkTime() {
        return lpWorkTime;
    }

    public void setLpWorkTime(Date lpWorkTime) {
        this.lpWorkTime = lpWorkTime;
    }

    public BigDecimal getPeriodOffline() {
        return periodOffline;
    }

    public void setPeriodOffline(BigDecimal periodOffline) {
        this.periodOffline = periodOffline;
    }

    public BigDecimal getPeriodOnline() {
        return periodOnline;
    }

    public void setPeriodOnline(BigDecimal periodOnline) {
        this.periodOnline = periodOnline;
    }

    public BigDecimal getFinishPeriodOffline() {
        return finishPeriodOffline;
    }

    public void setFinishPeriodOffline(BigDecimal finishPeriodOffline) {
        this.finishPeriodOffline = finishPeriodOffline;
    }

    public BigDecimal getFinishPeriodOnline() {
        return finishPeriodOnline;
    }

    public void setFinishPeriodOnline(BigDecimal finishPeriodOnline) {
        this.finishPeriodOnline = finishPeriodOnline;
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