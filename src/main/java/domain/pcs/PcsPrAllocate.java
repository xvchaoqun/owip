package domain.pcs;

import com.alibaba.fastjson.JSONObject;
import domain.base.MetaType;
import org.apache.commons.lang3.StringUtils;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PcsPrAllocate implements Serializable {

    public int getTotalPrCount(){

        int totalPrCount = 0;
        Map<Integer, Integer> prCountMap = getPrCountMap();
        if(prCountMap!=null) {
            for (Integer value : prCountMap.values()) {
                totalPrCount += NumberUtils.trimToZero(value);
            }
        }

        return totalPrCount;
    }

    public Map<Integer, Integer> getPrCountMap(){

        if(StringUtils.isBlank(prCount)) return null;

        Map<Integer, Integer> prCountMap = new HashMap<>();
        Map<Integer, MetaType> prTypes = CmTag.getMetaTypes("mc_pcs_pr_type");
        JSONObject jo = JSONObject.parseObject(prCount);
        if(jo!=null) {
            for (MetaType prType : prTypes.values()) {
                Integer prCount = jo.getInteger(prType.getId() + "");
                if (prCount != null) {
                    prCountMap.put(prType.getId(), prCount);
                }
            }
        }

        return prCountMap;
    }

    private Integer id;

    private Integer configId;

    private Integer partyId;

    private Integer candidateCount;

    private String prCount;

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

    public Integer getCandidateCount() {
        return candidateCount;
    }

    public void setCandidateCount(Integer candidateCount) {
        this.candidateCount = candidateCount;
    }

    public String getPrCount() {
        return prCount;
    }

    public void setPrCount(String prCount) {
        this.prCount = prCount == null ? null : prCount.trim();
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