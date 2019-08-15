package domain.pm;

import java.io.Serializable;

public class PmQuarterParty implements Serializable {
    private Integer id;

    private Integer quarterId;

    private Integer partyId;

    private String partyName;

    private Integer branchNum;

    private Integer exculdeBranchNum;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Integer quarterId) {
        this.quarterId = quarterId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getExculdeBranchNum() {
        return exculdeBranchNum;
    }

    public void setExculdeBranchNum(Integer exculdeBranchNum) {
        this.exculdeBranchNum = exculdeBranchNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}