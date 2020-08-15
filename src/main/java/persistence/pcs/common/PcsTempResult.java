package persistence.pcs.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PcsTempResult implements Serializable {

    private static final long serialVersionUID = 5713312390601875781L;

    public Integer inspectorId;
    //同意测评说明
    public boolean agree;
    public boolean mobileAgree;

    //<type, Set<userId>>  一下阶段推荐结果
    public Map<Byte, Set<Integer>> firstResultMap;
    //<type_candidateUserId, status>  二下阶段推荐人推荐结果
    public Map<String, Byte> secondResultMap;
    //<type_candidateUserId_4, userId>    二下阶段其他推荐人
    public Map<String, Integer> otherResultMap;

    public PcsTempResult() {
        firstResultMap = new LinkedHashMap<>();
        secondResultMap = new LinkedHashMap<>();
        otherResultMap = new LinkedHashMap<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public boolean isMobileAgree() {
        return mobileAgree;
    }

    public void setMobileAgree(boolean mobileAgree) {
        this.mobileAgree = mobileAgree;
    }

    public Map<Byte, Set<Integer>> getFirstResultMap() {
        return firstResultMap;
    }

    public PcsTempResult setFirstResultMap(Map<Byte, Set<Integer>> firstResultMap) {
        this.firstResultMap = firstResultMap;
        return this;
    }

    public Map<String, Byte> getSecondResultMap() {
        return secondResultMap;
    }

    public PcsTempResult setSecondResultMap(Map<String, Byte> secondResultMap) {
        this.secondResultMap = secondResultMap;
        return this;
    }

    public Map<String, Integer> getOtherResultMap() {
        return otherResultMap;
    }

    public PcsTempResult setOtherResultMap(Map<String, Integer> otherResultMap) {
        this.otherResultMap = otherResultMap;
        return this;
    }
}
