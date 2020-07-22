package persistence.dr.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DrTempResult implements Serializable {

    private static final long serialVersionUID = 5713315990601875781L;
    public Integer inspectorId;
    //同意测评说明
    public boolean agree;
    public boolean mobileAgree;

    //<postId_userId, status>   候选人推荐结果
    public Map<String, Byte> candidateMap;
    //<postId_userId, realname> 另选推荐人
    public Map<String, String> otherMap;
    //<postId, Set<realname>> 推荐人
    public Map<Integer, Set<String>> realnameSetMap;

    public DrTempResult() {

		candidateMap = new LinkedHashMap<>();
		otherMap = new LinkedHashMap<>();
		realnameSetMap = new LinkedHashMap<>();
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Map<String, Byte> getCandidateMap() {
        return candidateMap;
    }

    public DrTempResult setCandidateMap(Map<String, Byte> candidateMap) {
        this.candidateMap = candidateMap;
        return this;
    }

    public Map<String, String> getOtherMap() {
        return otherMap;
    }

    public DrTempResult setOtherMap(Map<String, String> otherMap) {
        this.otherMap = otherMap;
        return this;
    }

    public Map<Integer, Set<String>> getRealnameSetMap() {
        return realnameSetMap;
    }

    public DrTempResult setRealnameSetMap(Map<Integer, Set<String>> realnameSetMap) {
        this.realnameSetMap = realnameSetMap;
        return this;
    }
}
