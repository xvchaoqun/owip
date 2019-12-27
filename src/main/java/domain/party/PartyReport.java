package domain.party;

import sys.tags.CmTag;

import java.io.Serializable;

public class PartyReport implements Serializable {

    public Party getParty(){
        return CmTag.getParty(partyId);
    }
    public Branch getBranch(){
        return CmTag.getBranch(branchId);
    }
    private Integer id;

    private Integer year;

    private Integer partyId;

    private String partyName;

    private Integer branchId;

    private String branchName;

    private String reportFile;

    private Byte evaResult;

    private String evaFile;

    private String remark;

    private Byte status;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile == null ? null : reportFile.trim();
    }

    public Byte getEvaResult() {
        return evaResult;
    }

    public void setEvaResult(Byte evaResult) {
        this.evaResult = evaResult;
    }

    public String getEvaFile() {
        return evaFile;
    }

    public void setEvaFile(String evaFile) {
        this.evaFile = evaFile == null ? null : evaFile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}