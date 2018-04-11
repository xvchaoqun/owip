package persistence.pcs.common;

import domain.party.PartyView;

public class PcsPrPartyBean extends PartyView {

    private Integer configId;
    private Byte stage;
    private Integer expectMemberCount;
    private Integer expectPositiveMemberCount;
    private Integer actualMemberCount;
    private Integer actualPositiveMemberCount;
    private Boolean hasReport;
    private Byte recommendStatus;
    private String checkRemark;

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

    public Byte getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Byte recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }
}