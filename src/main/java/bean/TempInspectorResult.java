package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TempInspectorResult implements Serializable {

    private static final long serialVersionUID = -3710621556485211047L;
    public int onlineId;
    public int status;
    //存储所有结果<postViewId_userId, option>
    public Map<String, Integer> optionIdMap;


    public TempInspectorResult() {
        this.optionIdMap = new HashMap<>();
    }

    public int getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(int onlineId) {
        this.onlineId = onlineId;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Map<String, Integer> getOptionIdMap() {
        return optionIdMap;
    }
    public void setOptionIdMap(Map<String, Integer> optionIdMap) {
        this.optionIdMap = optionIdMap;
    }
}
