package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TempResult implements Serializable {

    private static final long serialVersionUID = -3493827992980228346L;
    public Integer inspectorId;
    //同意测评说明
    public boolean agree;
    public boolean mobileAgree;
    // 可选单位
    public Set<Integer> unitIds;
    //<postId, nameCodes>
    public Map<Integer, String> otherResultMap;
    //<onlineId, TempInspectorResult>
    public Map<String, TempInspectorResult> tempInspectorResultMap;

    public TempResult() {

        tempInspectorResultMap = new HashMap<String, TempInspectorResult>();
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

    public Set<Integer> getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(Set<Integer> unitIds) {
        this.unitIds = unitIds;
    }

    public Map<Integer, String> getOtherResultMap() {
        return otherResultMap;
    }

    public void setOtherResultMap(Map<Integer, String> otherResultMap) {
        this.otherResultMap = otherResultMap;
    }

    public Map<String, TempInspectorResult> getTempInspectorResultMap() {
        return tempInspectorResultMap;
    }

    public void setTempInspectorResultMap(Map<String, TempInspectorResult> tempInspectorResultMap) {
        this.tempInspectorResultMap = tempInspectorResultMap;
    }

    @Override
    public String toString() {
        return "TempResult [inspectorId=" + inspectorId + ", agree=" + agree + ", mobileAgree=" + isMobileAgree() +
                ", unitIds=" + unitIds + ", otherResultMap=" + otherResultMap + ", tempInspectorResultMap="
                + tempInspectorResultMap + "]";
    }
}
