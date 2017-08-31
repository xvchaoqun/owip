package domain.pcs;

import java.io.Serializable;
import java.util.Date;

public class PcsPrRecommend implements Serializable {
    private Integer id;

    private Integer configId;

    private Byte stage;

    private Integer partyId;

    private Integer expectMemberCount;

    private Integer expectPositiveMemberCount;

    private Integer actualMemberCount;

    private Integer actualPositiveMemberCount;

    private Boolean hasReport;

    private Integer reportUserId;

    private Date reportTime;

    private Byte status;

    private String checkRemark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getExpectMemberCount() {
        return expectMemberCount;
    }

    public void setExpectMemberCount(Integer expectMemberCount) {
        this.expectMemberCount = expectMemberCount;
    }

    public Integer getExpectPositiveMemberCount() {
        return expectPositiveMemberCount;
    }

    public void setExpectPositiveMemberCount(Integer expectPositiveMemberCount) {
        this.expectPositiveMemberCount = expectPositiveMemberCount;
    }

    public Integer getActualMemberCount() {
        return actualMemberCount;
    }

    public void setActualMemberCount(Integer actualMemberCount) {
        this.actualMemberCount = actualMemberCount;
    }

    public Integer getActualPositiveMemberCount() {
        return actualPositiveMemberCount;
    }

    public void setActualPositiveMemberCount(Integer actualPositiveMemberCount) {
        this.actualPositiveMemberCount = actualPositiveMemberCount;
    }

    public Boolean getHasReport() {
        return hasReport;
    }

    public void setHasReport(Boolean hasReport) {
        this.hasReport = hasReport;
    }

    public Integer getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Integer reportUserId) {
        this.reportUserId = reportUserId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }
}