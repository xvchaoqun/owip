package persistence.pcs.common;

import domain.pcs.PcsBranch;

import java.io.Serializable;

public class PcsBranchBean extends PcsBranch implements Serializable {

    private Integer recommendId;
    private Byte stage;
    private Integer realMemberCount;
    private Integer expectMemberCount;
    private Integer actualMemberCount;
    private Boolean isFinished;

    private static final long serialVersionUID = 1L;

    public Integer getRecommendId() {
        return recommendId;
    }

    public PcsBranchBean setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
        return this;
    }

    public Byte getStage() {
        return stage;
    }

    public PcsBranchBean setStage(Byte stage) {
        this.stage = stage;
        return this;
    }

    public Integer getRealMemberCount() {
        return realMemberCount;
    }

    public void setRealMemberCount(Integer realMemberCount) {
        this.realMemberCount = realMemberCount;
    }

    public Integer getExpectMemberCount() {
        return expectMemberCount;
    }

    public PcsBranchBean setExpectMemberCount(Integer expectMemberCount) {
        this.expectMemberCount = expectMemberCount;
        return this;
    }

    public Integer getActualMemberCount() {
        return actualMemberCount;
    }

    public PcsBranchBean setActualMemberCount(Integer actualMemberCount) {
        this.actualMemberCount = actualMemberCount;
        return this;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public PcsBranchBean setIsFinished(Boolean finished) {
        isFinished = finished;
        return this;
    }
}