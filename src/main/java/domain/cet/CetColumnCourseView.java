package domain.cet;

import java.io.Serializable;

public class CetColumnCourseView implements Serializable {
    private Integer id;

    private Integer columnId;

    private Integer courseId;

    private Integer sortOrder;

    private String remark;

    private Integer fColumnId;

    private String fColumnName;

    private String columnName;

    private String courseName;

    private String realname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public Integer getfColumnId() {
        return fColumnId;
    }

    public void setfColumnId(Integer fColumnId) {
        this.fColumnId = fColumnId;
    }

    public String getfColumnName() {
        return fColumnName;
    }

    public void setfColumnName(String fColumnName) {
        this.fColumnName = fColumnName == null ? null : fColumnName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }
}