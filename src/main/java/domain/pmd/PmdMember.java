package domain.pmd;

import domain.sys.SysUserView;
import service.pmd.PmdMemberPayService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PmdMember implements Serializable {

    public SysUserView getUser() {

        return CmTag.getUserById(userId);
    }

    public SysUserView getChargeUser() {

        return CmTag.getUserById(chargeUserId);
    }

    public int getPayStatus() {

        PmdMemberPayService pmdMemberPayService = CmTag.getBean(PmdMemberPayService.class);
        return pmdMemberPayService.getPayStatus(id);
    }

    private Integer id;

    private Integer monthId;

    private Integer userId;

    private Date payMonth;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private Boolean hasSalary;

    private String talentTitle;

    private String postClass;

    private String mainPostLevel;

    private String authorizedType;

    private String staffType;

    private BigDecimal salary;

    private Byte normType;

    private String normName;

    private BigDecimal normDuePay;

    private Integer normId;

    private Integer normValueId;

    private String normDisplayName;

    private BigDecimal duePay;

    private BigDecimal realPay;

    private Boolean isDelay;

    private String delayReason;

    private Boolean hasPay;

    private Boolean isOnlinePay;

    private Date payTime;

    private Integer chargeUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Date payMonth) {
        this.payMonth = payMonth;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Boolean getHasSalary() {
        return hasSalary;
    }

    public void setHasSalary(Boolean hasSalary) {
        this.hasSalary = hasSalary;
    }

    public String getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(String talentTitle) {
        this.talentTitle = talentTitle == null ? null : talentTitle.trim();
    }

    public String getPostClass() {
        return postClass;
    }

    public void setPostClass(String postClass) {
        this.postClass = postClass == null ? null : postClass.trim();
    }

    public String getMainPostLevel() {
        return mainPostLevel;
    }

    public void setMainPostLevel(String mainPostLevel) {
        this.mainPostLevel = mainPostLevel == null ? null : mainPostLevel.trim();
    }

    public String getAuthorizedType() {
        return authorizedType;
    }

    public void setAuthorizedType(String authorizedType) {
        this.authorizedType = authorizedType == null ? null : authorizedType.trim();
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType == null ? null : staffType.trim();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Byte getNormType() {
        return normType;
    }

    public void setNormType(Byte normType) {
        this.normType = normType;
    }

    public String getNormName() {
        return normName;
    }

    public void setNormName(String normName) {
        this.normName = normName == null ? null : normName.trim();
    }

    public BigDecimal getNormDuePay() {
        return normDuePay;
    }

    public void setNormDuePay(BigDecimal normDuePay) {
        this.normDuePay = normDuePay;
    }

    public Integer getNormId() {
        return normId;
    }

    public void setNormId(Integer normId) {
        this.normId = normId;
    }

    public Integer getNormValueId() {
        return normValueId;
    }

    public void setNormValueId(Integer normValueId) {
        this.normValueId = normValueId;
    }

    public String getNormDisplayName() {
        return normDisplayName;
    }

    public void setNormDisplayName(String normDisplayName) {
        this.normDisplayName = normDisplayName == null ? null : normDisplayName.trim();
    }

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }

    public BigDecimal getRealPay() {
        return realPay;
    }

    public void setRealPay(BigDecimal realPay) {
        this.realPay = realPay;
    }

    public Boolean getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Boolean isDelay) {
        this.isDelay = isDelay;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason == null ? null : delayReason.trim();
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Boolean getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Boolean isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getChargeUserId() {
        return chargeUserId;
    }

    public void setChargeUserId(Integer chargeUserId) {
        this.chargeUserId = chargeUserId;
    }
}