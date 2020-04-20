package bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class DrTempResult implements Serializable {

    private static final long serialVersionUID = 5713315990601875781L;
    public Integer inspectorId;
    //同意测评说明
    public boolean agree;
    public boolean mobileAgree;
    // 可选单位
    public Set<Integer> unitIds;
    //<postId, names> 另选他人得票
    public Map<Integer, String> otherResultMap;
    //<postId_userId, option>   管理员设置的候选人得票
    public Map<String, Integer> rawOptionMap;

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

    public Map<String, Integer> getRawOptionMap() {
        return rawOptionMap;
    }

    public void setRawOptionMap(Map<String, Integer> rawOptionMap) {
        this.rawOptionMap = rawOptionMap;
    }

    @Override
    public String toString() {
        return "DrTempResult{" +
                "inspectorId=" + inspectorId +
                ", agree=" + agree +
                ", mobileAgree=" + mobileAgree +
                ", unitIds=" + unitIds +
                ", otherResultMap=" + otherResultMap +
                ", rawOptionMap=" + rawOptionMap +
                '}';
    }
}
