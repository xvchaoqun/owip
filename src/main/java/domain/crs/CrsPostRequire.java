package domain.crs;

import sys.helper.CrsHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrsPostRequire implements Serializable {

    public List<CrsRequireRule> getRules(){
        Map<Integer, CrsRequireRule> crsRequireRules = CrsHelper.getCrsRequireRules(id);
        return new ArrayList<>(crsRequireRules.values());
    }

    private Integer id;

    private String name;

    private Integer sortOrder;

    private String remark;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}