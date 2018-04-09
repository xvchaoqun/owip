package domain.cet;

import java.io.Serializable;

public class CetCourseTypeView implements Serializable {
    private Integer id;

    private String name;

    private Integer sortOrder;

    private String remark;

    private Boolean isDeleted;

    private Integer courseNum;

    private Integer traineeNum;

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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public Integer getTraineeNum() {
        return traineeNum;
    }

    public void setTraineeNum(Integer traineeNum) {
        this.traineeNum = traineeNum;
    }
}