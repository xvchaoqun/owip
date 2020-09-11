package domain.pcs;

import java.io.Serializable;

public class PcsRecommend implements Serializable {
    private Integer id;

    private Integer partyId;

    private Integer branchId;

    private Integer expectMemberCount;

    private Integer actualMemberCount;

    private Integer configId;

    private Boolean isFinished;

    private Byte stage;

    private Integer memberCount;

    private Integer positiveCount;

    private Integer studentMemberCount;

    private Integer teacherMemberCount;

    private Integer retireMemberCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
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

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(Integer positiveCount) {
        this.positiveCount = positiveCount;
    }

    public Integer getStudentMemberCount() {
        return studentMemberCount;
    }

    public void setStudentMemberCount(Integer studentMemberCount) {
        this.studentMemberCount = studentMemberCount;
    }

    public Integer getTeacherMemberCount() {
        return teacherMemberCount;
    }

    public void setTeacherMemberCount(Integer teacherMemberCount) {
        this.teacherMemberCount = teacherMemberCount;
    }

    public Integer getRetireMemberCount() {
        return retireMemberCount;
    }

    public void setRetireMemberCount(Integer retireMemberCount) {
        this.retireMemberCount = retireMemberCount;
    }
}