package domain.crs;

import java.io.Serializable;
import java.util.List;

public class CrsRequireRule implements Serializable {

    private List<CrsRuleItem> ruleItems;

    public List<CrsRuleItem> getRuleItems() {
        return ruleItems;
    }

    public void setRuleItems(List<CrsRuleItem> ruleItems) {
        this.ruleItems = ruleItems;
    }

    private Integer id;

    private Integer postRequireId;

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

    public Integer getPostRequireId() {
        return postRequireId;
    }

    public void setPostRequireId(Integer postRequireId) {
        this.postRequireId = postRequireId;
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