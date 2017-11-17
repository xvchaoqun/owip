package domain.pmd;

import java.io.Serializable;

public class PmdPayParty implements Serializable {
    private Integer partyId;

    private Integer monthId;

    private static final long serialVersionUID = 1L;

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