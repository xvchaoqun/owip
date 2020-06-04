package domain.base;

import java.io.Serializable;

public class MetaTypeView implements Serializable {
    private Integer id;

    private Integer classId;

    private String name;

    private String code;

    private Boolean boolAttr;

    private String extraAttr;

    private String remark;

    private Integer sortOrder;

    private Boolean available;

    private String className;

    private String firstLevel;

    private String secondLevel;

    private String classCode;

    private Integer classSortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public Boolean getBoolAttr() {
        return boolAttr;
    }

    public void setBoolAttr(Boolean boolAttr) {
        this.boolAttr = boolAttr;
    }

    public String getExtraAttr() {
        return extraAttr;
    }

    public void setExtraAttr(String extraAttr) {
        this.extraAttr = extraAttr == null ? null : extraAttr.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel == null ? null : firstLevel.trim();
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel == null ? null : secondLevel.trim();
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Integer getClassSortOrder() {
        return classSortOrder;
    }

    public void setClassSortOrder(Integer classSortOrder) {
        this.classSortOrder = classSortOrder;
    }
}