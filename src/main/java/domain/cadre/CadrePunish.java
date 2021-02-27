package domain.cadre;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.util.Date;

public class CadrePunish implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Date punishTime;

    private String name;

    private String unit;

    @SignRes
    private String proof;

    private String proofFilename;

    private Boolean listInAd;

    private Integer sortOrder;

    private Byte status;

    private String remark;

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

    public Date getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(Date punishTime) {
        this.punishTime = punishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof == null ? null : proof.trim();
    }

    public String getProofFilename() {
        return proofFilename;
    }

    public void setProofFilename(String proofFilename) {
        this.proofFilename = proofFilename == null ? null : proofFilename.trim();
    }

    public Boolean getListInAd() {
        return listInAd;
    }

    public void setListInAd(Boolean listInAd) {
        this.listInAd = listInAd;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}