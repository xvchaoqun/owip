package domain.dispatch;

import java.io.Serializable;
import java.util.Date;

public class DispatchType implements Serializable {
    private Integer id;

    private String name;

    private Integer attr;

    private Short year;

    private Date createTime;

    private Integer sortOrder;

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

    public Integer getAttr() {
        return attr;
    }

    public void setAttr(Integer attr) {
        this.attr = attr;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}