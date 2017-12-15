package domain.pmd;

import domain.sys.SysUserView;
import persistence.pmd.PmdConfigMemberMapper;
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

    public PmdMemberPayView getPmdMemberPayView(){

        PmdMemberPayService pmdMemberPayService = CmTag.getBean(PmdMemberPayService.class);
        return pmdMemberPayService.get(id);
    }

    public PmdConfigMember getPmdConfigMember(){

        PmdConfigMemberMapper pmdConfigMemberMapper = CmTag.getBean(PmdConfigMemberMapper.class);
        return pmdConfigMemberMapper.selectByPrimaryKey(userId);
    }

    private Integer id;

    private Integer monthId;

    private Integer userId;

    private Date payMonth;

    private Integer partyId;

    private Integer branchId;

    private Byte type;

    private Integer configMemberTypeId;

    private String configMemberTypeName;

    private Integer configMemberTypeNormId;

    private String configMemberTypeNormName;

    private BigDecimal configMemberDuePay;

    private Boolean needSetSalary;

    private Boolean hasSalary;

    private String talentTitle;

    private String postClass;

    private String mainPostLevel;

    private String proPostLevel;

    private String manageLevel;

    private String officeLevel;

    private String authorizedType;

    private String staffType;

    private BigDecimal salary;

    private Integer normId;

    private Integer normValueId;

    private String duePayReason;

    private BigDecimal duePay;

    private BigDecimal realPay;

    private Boolean isDelay;

    private String delayReason;

    private Boolean hasPay;

    private Boolean isSelfPay;

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

    public Integer getConfigMemberTypeId() {
        return configMemberTypeId;
    }

    public void setConfigMemberTypeId(Integer configMemberTypeId) {
        this.configMemberTypeId = configMemberTypeId;
    }

    public String getConfigMemberTypeName() {
        return configMemberTypeName;
    }

    public void setConfigMemberTypeName(String configMemberTypeName) {
        this.configMemberTypeName = configMemberTypeName == null ? null : configMemberTypeName.trim();
    }

    public Integer getConfigMemberTypeNormId() {
        return configMemberTypeNormId;
    }

    public void setConfigMemberTypeNormId(Integer configMemberTypeNormId) {
        this.configMemberTypeNormId = configMemberTypeNormId;
    }

    public String getConfigMemberTypeNormName() {
        return configMemberTypeNormName;
    }

    public void setConfigMemberTypeNormName(String configMemberTypeNormName) {
        this.configMemberTypeNormName = configMemberTypeNormName == null ? null : configMemberTypeNormName.trim();
    }

    public BigDecimal getConfigMemberDuePay() {
        return configMemberDuePay;
    }

    public void setConfigMemberDuePay(BigDecimal configMemberDuePay) {
        this.configMemberDuePay = configMemberDuePay;
    }

    public Boolean getNeedSetSalary() {
        return needSetSalary;
    }

    public void setNeedSetSalary(Boolean needSetSalary) {
        this.needSetSalary = needSetSalary;
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

    public String getProPostLevel() {
        return proPostLevel;
    }

    public void setProPostLevel(String proPostLevel) {
        this.proPostLevel = proPostLevel == null ? null : proPostLevel.trim();
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel == null ? null : manageLevel.trim();
    }

    public String getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(String officeLevel) {
        this.officeLevel = officeLevel == null ? null : officeLevel.trim();
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

    public String getDuePayReason() {
        return duePayReason;
    }

    public void setDuePayReason(String duePayReason) {
        this.duePayReason = duePayReason == null ? null : duePayReason.trim();
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

    public Boolean getIsSelfPay() {
        return isSelfPay;
    }

    public void setIsSelfPay(Boolean isSelfPay) {
        this.isSelfPay = isSelfPay;
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