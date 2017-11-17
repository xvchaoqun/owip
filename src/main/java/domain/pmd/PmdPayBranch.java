package domain.pmd;

import java.io.Serializable;

public class PmdPayBranch implements Serializable {
    private Integer branchId;

    private Integer partyId;

    private Integer monthId;

    private static final long serialVersionUID = 1L;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getMonthId() {
        return monthId;
    }

    public void setMonthId(Integer monthId) {
        this.monthId = monthId;
    }
}