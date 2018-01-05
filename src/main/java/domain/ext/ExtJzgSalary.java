package domain.ext;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExtJzgSalary implements Serializable {
    private Integer id;

    private String zgh;

    private String xm;

    private String rq;

    private BigDecimal xpgz;

    private BigDecimal xjgz;

    private BigDecimal gwgz;

    private BigDecimal gwjt;

    private BigDecimal zwbt;

    private BigDecimal zwbt1;

    private BigDecimal shbt;

    private BigDecimal sbf;

    private BigDecimal xlf;

    private BigDecimal gzcx;

    private BigDecimal sygr;

    private BigDecimal yanglaogr;

    private BigDecimal yiliaogr;

    private BigDecimal njgr;

    private BigDecimal zfgjj;

    private BigDecimal zzryhj;

    private BigDecimal xpryhj;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZgh() {
        return zgh;
    }

    public void setZgh(String zgh) {
        this.zgh = zgh == null ? null : zgh.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq == null ? null : rq.trim();
    }

    public BigDecimal getXpgz() {
        return xpgz;
    }

    public void setXpgz(BigDecimal xpgz) {
        this.xpgz = xpgz;
    }

    public BigDecimal getXjgz() {
        return xjgz;
    }

    public void setXjgz(BigDecimal xjgz) {
        this.xjgz = xjgz;
    }

    public BigDecimal getGwgz() {
        return gwgz;
    }

    public void setGwgz(BigDecimal gwgz) {
        this.gwgz = gwgz;
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

    public BigDecimal getSygr() {
        return sygr;
    }

    public void setSygr(BigDecimal sygr) {
        this.sygr = sygr;
    }

    public BigDecimal getYanglaogr() {
        return yanglaogr;
    }

    public void setYanglaogr(BigDecimal yanglaogr) {
        this.yanglaogr = yanglaogr;
    }

    public BigDecimal getYiliaogr() {
        return yiliaogr;
    }

    public void setYiliaogr(BigDecimal yiliaogr) {
        this.yiliaogr = yiliaogr;
    }

    public BigDecimal getNjgr() {
        return njgr;
    }

    public void setNjgr(BigDecimal njgr) {
        this.njgr = njgr;
    }

    public BigDecimal getZfgjj() {
        return zfgjj;
    }

    public void setZfgjj(BigDecimal zfgjj) {
        this.zfgjj = zfgjj;
    }

    public BigDecimal getZzryhj() {
        return zzryhj;
    }

    public void setZzryhj(BigDecimal zzryhj) {
        this.zzryhj = zzryhj;
    }

    public BigDecimal getXpryhj() {
        return xpryhj;
    }

    public void setXpryhj(BigDecimal xpryhj) {
        this.xpryhj = xpryhj;
    }
}