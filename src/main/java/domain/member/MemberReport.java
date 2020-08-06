package domain.member;

import domain.party.Party;
import domain.sys.SysUserView;
import sys.jackson.SignRes;
import sys.tags.CmTag;

import java.io.Serializable;

public class MemberReport implements Serializable {

    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }

    public Party getParty(){
        return CmTag.getParty(partyId);
    }

    private Integer id;

    private Integer year;

    private Integer userId;

    private Integer partyId;

    private String partyName;

    private Integer branchId;

    private String branchName;

    @SignRes
    private String reportFile;

    private Byte evaResult;

    @SignRes
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