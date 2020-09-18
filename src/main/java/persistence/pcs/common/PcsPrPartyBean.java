package persistence.pcs.common;

import domain.pcs.PcsParty;

public class PcsPrPartyBean extends PcsParty {

    private Byte stage;
    private Integer recommendBranchCount;
    private Integer recommendMemberCount;
    private Integer recommendStudentCount;
    private Integer recommendTeacherCount;
    private Integer recommendRetireCount;
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

    public Integer getRecommendBranchCount() {
        return recommendBranchCount;
    }

    public void setRecommendBranchCount(Integer recommendBranchCount) {
        this.recommendBranchCount = recommendBranchCount;
    }

    public Integer getRecommendMemberCount() {
        return recommendMemberCount;
    }

    public void setRecommendMemberCount(Integer recommendMemberCount) {
        this.recommendMemberCount = recommendMemberCount;
    }

    public Integer getRecommendStudentCount() {
        return recommendStudentCount;
    }

    public void setRecommendStudentCount(Integer recommendStudentCount) {
        this.recommendStudentCount = recommendStudentCount;
    }

    public Integer getRecommendTeacherCount() {
        return recommendTeacherCount;
    }

    public void setRecommendTeacherCount(Integer recommendTeacherCount) {
        this.recommendTeacherCount = recommendTeacherCount;
    }

    public Integer getRecommendRetireCount() {
        return recommendRetireCount;
    }

    public void setRecommendRetireCount(Integer recommendRetireCount) {
        this.recommendRetireCount = recommendRetireCount;
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