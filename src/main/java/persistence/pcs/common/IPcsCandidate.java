package persistence.pcs.common;

import domain.pcs.PcsCandidate;

/**
 * Created by lm on 2017/8/27.
 */
public class IPcsCandidate extends PcsCandidate {

    private String partyIds;
    private String branchIds;
    private Integer branchCount;
    private Integer memberCount;
    private Integer expectMemberCount;
    private Integer actualMemberCount;
    private Integer totalVote;
    private Integer totalPositiveVote;
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

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

    public Integer getTotalPositiveVote() {
        return totalPositiveVote;
    }

    public void setTotalPositiveVote(Integer totalPositiveVote) {
        this.totalPositiveVote = totalPositiveVote;
    }

    public Integer getChosenId() {
        return chosenId;
    }

    public void setChosenId(Integer chosenId) {
        this.chosenId = chosenId;
    }
}
