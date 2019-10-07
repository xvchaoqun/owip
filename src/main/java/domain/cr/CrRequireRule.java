package domain.cr;

import java.io.Serializable;
import java.util.List;

public class CrRequireRule implements Serializable {

    private List<CrRuleItem> ruleItems;

    public List<CrRuleItem> getRuleItems() {
        return ruleItems;
    }

    public void setRuleItems(List<CrRuleItem> ruleItems) {
        this.ruleItems = ruleItems;
    }

    private Integer id;

    private Integer requireId;

    private Integer itemNum;

    private String name;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequireId() {
        return requireId;
    }

    public void setRequireId(Integer requireId) {
        this.requireId = requireId;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
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
}