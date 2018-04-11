package persistence.pcs.common;

import domain.pcs.PcsPrAllocate;

/**
 * Created by lm on 2017/8/29.
 */
public class PcsPrAllocateBean extends PcsPrAllocate {

    private Integer partyId;
    private String partyName;
    private Integer positiveCount;
    private Integer memberCount;

    @Override
    public Integer getPartyId() {
        return partyId;
    }

    @Override
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(Integer positiveCount) {
        this.positiveCount = positiveCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
