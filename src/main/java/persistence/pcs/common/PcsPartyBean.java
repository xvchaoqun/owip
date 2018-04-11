package persistence.pcs.common;

import domain.party.PartyView;

public class PcsPartyBean extends PartyView {

    private Integer configId;
    private Byte stage;
    private Integer expectMemberCount;
    private Integer actualMemberCount;
    private Integer reportId;

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

    public Integer getExpectMemberCount() {
        return expectMemberCount;
    }

    public void setExpectMemberCount(Integer expectMemberCount) {
        this.expectMemberCount = expectMemberCount;
    }

    public Integer getActualMemberCount() {
        return actualMemberCount;
    }

    public void setActualMemberCount(Integer actualMemberCount) {
        this.actualMemberCount = actualMemberCount;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
}