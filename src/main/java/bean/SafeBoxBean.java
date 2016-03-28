package bean;

import domain.Unit;
import org.apache.commons.lang3.StringUtils;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fafa on 2016/3/13.
 */
public class SafeBoxBean {

    public String getUnits(){

        List<String> unitNameList = new ArrayList<>();
        String[] unitIdStrArray = unitIds.split(",");
        for (String unitIdStr : unitIdStrArray) {
            Unit unit = CmTag.getUnit(Integer.parseInt(unitIdStr));
            unitNameList.add(unit.getName());
        }
        return StringUtils.join(unitNameList, ",");
    }

    private  Integer id;
    private String code;
    private String remark;
    private Integer totalCount;
    private String unitIds;
    private Integer keepCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public Integer getKeepCount() {
        return keepCount;
    }

    public void setKeepCount(Integer keepCount) {
        this.keepCount = keepCount;
    }
}
