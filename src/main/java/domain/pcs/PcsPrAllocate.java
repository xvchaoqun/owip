package domain.pcs;

import java.io.Serializable;

public class PcsPrAllocate implements Serializable {
    private Integer id;

    private Integer configId;

    private Integer partyId;

    private Integer proCount;

    private Integer stuCount;

    private Integer retireCount;

    private Integer femaleCount;

    private Integer minorityCount;

    private Integer underFiftyCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }

    public Integer getStuCount() {
        return stuCount;
    }

    public void setStuCount(Integer stuCount) {
        this.stuCount = stuCount;
    }

    public Integer getRetireCount() {
        return retireCount;
    }

    public void setRetireCount(Integer retireCount) {
        this.retireCount = retireCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    public Integer getMinorityCount() {
        return minorityCount;
    }

    public void setMinorityCount(Integer minorityCount) {
        this.minorityCount = minorityCount;
    }

    public Integer getUnderFiftyCount() {
        return underFiftyCount;
    }

    public void setUnderFiftyCount(Integer underFiftyCount) {
        this.underFiftyCount = underFiftyCount;
    }
}