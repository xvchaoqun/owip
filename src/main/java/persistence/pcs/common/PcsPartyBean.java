package persistence.pcs.common;

import domain.pcs.PcsParty;

public class PcsPartyBean extends PcsParty {

    private Byte stage;
    private Integer expectMemberCount;
    private Integer actualMemberCount;
    private Integer reportId;

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