package persistence.pcs.common;

import domain.pcs.PcsParty;

public class PcsPrPartyBean extends PcsParty {

    private Byte stage;
    private Integer realBranchCount;
    private Integer realMemberCount;
    private Integer realPositiveCount;
    private Integer realStudentMemberCount;
    private Integer realTeacherMemberCount;
    private Integer realRetireMemberCount;
    private Integer expectMemberCount;
    private Integer expectPositiveMemberCount;
    private Integer actualMemberCount;
    private Integer actualPositiveMemberCount;
    private Boolean hasReport;
    private Byte recommendStatus;
    private String checkRemark;

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Integer getRealBranchCount() {
        return realBranchCount;
    }

    public void setRealBranchCount(Integer realBranchCount) {
        this.realBranchCount = realBranchCount;
    }

    public Integer getRealMemberCount() {
        return realMemberCount;
    }

    public void setRealMemberCount(Integer realMemberCount) {
        this.realMemberCount = realMemberCount;
    }

    public Integer getRealPositiveCount() {
        return realPositiveCount;
    }

    public void setRealPositiveCount(Integer realPositiveCount) {
        this.realPositiveCount = realPositiveCount;
    }

    public Integer getRealStudentMemberCount() {
        return realStudentMemberCount;
    }

    public void setRealStudentMemberCount(Integer realStudentMemberCount) {
        this.realStudentMemberCount = realStudentMemberCount;
    }

    public Integer getRealTeacherMemberCount() {
        return realTeacherMemberCount;
    }

    public void setRealTeacherMemberCount(Integer realTeacherMemberCount) {
        this.realTeacherMemberCount = realTeacherMemberCount;
    }

    public Integer getRealRetireMemberCount() {
        return realRetireMemberCount;
    }

    public void setRealRetireMemberCount(Integer realRetireMemberCount) {
        this.realRetireMemberCount = realRetireMemberCount;
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