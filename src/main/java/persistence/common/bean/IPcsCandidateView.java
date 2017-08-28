package persistence.common.bean;

import domain.pcs.PcsCandidateView;

/**
 * Created by lm on 2017/8/27.
 */
public class IPcsCandidateView extends PcsCandidateView {

    private String partyIds;
    private String branchIds;
    private Integer branchCount;
    private Integer memberCount;
    private Integer expectMemberCount;
    private Integer actualMemberCount;
    private Integer chosenId;

    public String getPartyIds() {
        return partyIds;
    }

    public void setPartyIds(String partyIds) {
        this.partyIds = partyIds;
    }

    public String getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(String branchIds) {
        this.branchIds = branchIds;
    }

    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
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

    public Integer getChosenId() {
        return chosenId;
    }

    public void setChosenId(Integer chosenId) {
        this.chosenId = chosenId;
    }
}
