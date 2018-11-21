package persistence.cadre.common;

import java.util.HashMap;
import java.util.Map;

public class CadreCompanyStatBean {

    // 兼职总数
    public int totalCount;
    // 每种兼职类型的数量
    public Map<Integer, Integer> typeMap = new HashMap<>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Map<Integer, Integer> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<Integer, Integer> typeMap) {
        this.typeMap = typeMap;
    }
}
