package domain;

import java.io.Serializable;

public class MetaClass implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String boolAttr;

    private String extraAttr;

    private Integer sortOrder;

    private Boolean available;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getBoolAttr() {
        return boolAttr;
    }

    public void setBoolAttr(String boolAttr) {
        this.boolAttr = boolAttr == null ? null : boolAttr.trim();
    }

    public String getExtraAttr() {
        return extraAttr;
    }

    public void setExtraAttr(String extraAttr) {
        this.extraAttr = extraAttr == null ? null : extraAttr.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}