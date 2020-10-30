package domain.pmd;

import domain.sys.SysUserView;
import service.pmd.PmdConfigMemberTypeService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmdConfigMember implements Serializable {

    public SysUserView getUser() {

        return CmTag.getUserById(userId);
    }

    public PmdConfigMemberType getPmdConfigMemberType() {

        PmdConfigMemberTypeService pmdConfigMemberTypeService = CmTag.getBean(PmdConfigMemberTypeService.class);
        return configMemberTypeId == null ? null : pmdConfigMemberTypeService.get(configMemberTypeId);
    }

    private Integer userId;

    private String mobile;

    private Byte configMemberType;

    private Integer configMemberTypeId;

    private Boolean isOnlinePay;

    private BigDecimal duePay;

    private Boolean hasSalary;

    private Boolean hasReset;

    private Boolean hasSetSalary;

    private BigDecimal retireSalary;

    private String salary;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Byte getConfigMemberType() {
        return configMemberType;
    }

    public void setConfigMemberType(Byte configMemberType) {
        this.configMemberType = configMemberType;
    }

    public Integer getConfigMemberTypeId() {
        return configMemberTypeId;
    }

    public void setConfigMemberTypeId(Integer configMemberTypeId) {
        this.configMemberTypeId = configMemberTypeId;
    }

    public Boolean getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Boolean isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }

    public Boolean getHasSalary() {
        return hasSalary;
    }

    public void setHasSalary(Boolean hasSalary) {
        this.hasSalary = hasSalary;
    }

    public Boolean getHasReset() {
        return hasReset;
    }

    public void setHasReset(Boolean hasReset) {
        this.hasReset = hasReset;
    }

    public Boolean getHasSetSalary() {
        return hasSetSalary;
    }

    public void setHasSetSalary(Boolean hasSetSalary) {
        this.hasSetSalary = hasSetSalary;
    }

    public BigDecimal getRetireSalary() {
        return retireSalary;
    }

    public void setRetireSalary(BigDecimal retireSalary) {
        this.retireSalary = retireSalary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }
}