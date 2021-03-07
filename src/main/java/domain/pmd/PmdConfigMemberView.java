package domain.pmd;

import domain.sys.SysUserView;
import service.pmd.PmdConfigMemberTypeService;
import sys.tags.CmTag;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmdConfigMemberView implements Serializable {
    public SysUserView getUser() {

        return CmTag.getUserById(userId);
    }

    public PmdConfigMemberType getPmdConfigMemberType() {

        PmdConfigMemberTypeService pmdConfigMemberTypeService = CmTag.getBean(PmdConfigMemberTypeService.class);
        return configMemberTypeId == null ? null : pmdConfigMemberTypeService.get(configMemberTypeId);
    }
    private Byte configMemberType;

    private Integer configMemberTypeId;

    private BigDecimal duePay;

    private String salary;

    private Integer userId;

    private Integer partyId;

    private Integer branchId;

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}