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

    private BigDecimal duePay;

    private Boolean hasSalary;

    private BigDecimal retireSalary;

    private Boolean hasSetSalary;

    private BigDecimal gwgz;

    private BigDecimal xjgz;

    private BigDecimal gwjt;

    private BigDecimal zwbt;

    private BigDecimal zwbt1;

    private BigDecimal shbt;

    private BigDecimal sbf;

    private BigDecimal xlf;

    private BigDecimal gzcx;

    private BigDecimal shiyebx;

    private BigDecimal yanglaobx;

    private BigDecimal yiliaobx;

    private BigDecimal gsbx;

    private BigDecimal shengyubx;

    private BigDecimal qynj;

    private BigDecimal zynj;

    private BigDecimal gjj;

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

    public BigDecimal getRetireSalary() {
        return retireSalary;
    }

    public void setRetireSalary(BigDecimal retireSalary) {
        this.retireSalary = retireSalary;
    }

    public Boolean getHasSetSalary() {
        return hasSetSalary;
    }

    public void setHasSetSalary(Boolean hasSetSalary) {
        this.hasSetSalary = hasSetSalary;
    }

    public BigDecimal getGwgz() {
        return gwgz;
    }

    public void setGwgz(BigDecimal gwgz) {
        this.gwgz = gwgz;
    }

    public BigDecimal getXjgz() {
        return xjgz;
    }

    public void setXjgz(BigDecimal xjgz) {
        this.xjgz = xjgz;
    }

    public BigDecimal getGwjt() {
        return gwjt;
    }

    public void setGwjt(BigDecimal gwjt) {
        this.gwjt = gwjt;
    }

    public BigDecimal getZwbt() {
        return zwbt;
    }

    public void setZwbt(BigDecimal zwbt) {
        this.zwbt = zwbt;
    }

    public BigDecimal getZwbt1() {
        return zwbt1;
    }

    public void setZwbt1(BigDecimal zwbt1) {
        this.zwbt1 = zwbt1;
    }

    public BigDecimal getShbt() {
        return shbt;
    }

    public void setShbt(BigDecimal shbt) {
        this.shbt = shbt;
    }

    public BigDecimal getSbf() {
        return sbf;
    }

    public void setSbf(BigDecimal sbf) {
        this.sbf = sbf;
    }

    public BigDecimal getXlf() {
        return xlf;
    }

    public void setXlf(BigDecimal xlf) {
        this.xlf = xlf;
    }

    public BigDecimal getGzcx() {
        return gzcx;
    }

    public void setGzcx(BigDecimal gzcx) {
        this.gzcx = gzcx;
    }

    public BigDecimal getShiyebx() {
        return shiyebx;
    }

    public void setShiyebx(BigDecimal shiyebx) {
        this.shiyebx = shiyebx;
    }

    public BigDecimal getYanglaobx() {
        return yanglaobx;
    }

    public void setYanglaobx(BigDecimal yanglaobx) {
        this.yanglaobx = yanglaobx;
    }

    public BigDecimal getYiliaobx() {
        return yiliaobx;
    }

    public void setYiliaobx(BigDecimal yiliaobx) {
        this.yiliaobx = yiliaobx;
    }

    public BigDecimal getGsbx() {
        return gsbx;
    }

    public void setGsbx(BigDecimal gsbx) {
        this.gsbx = gsbx;
    }

    public BigDecimal getShengyubx() {
        return shengyubx;
    }

    public void setShengyubx(BigDecimal shengyubx) {
        this.shengyubx = shengyubx;
    }

    public BigDecimal getQynj() {
        return qynj;
    }

    public void setQynj(BigDecimal qynj) {
        this.qynj = qynj;
    }

    public BigDecimal getZynj() {
        return zynj;
    }

    public void setZynj(BigDecimal zynj) {
        this.zynj = zynj;
    }

    public BigDecimal getGjj() {
        return gjj;
    }

    public void setGjj(BigDecimal gjj) {
        this.gjj = gjj;
    }
}