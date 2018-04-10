package domain.cet;

import java.io.Serializable;
import java.math.BigDecimal;

public class CetPlanCourseObjResult implements Serializable {
    private Integer id;

    private Integer planCourseObjId;

    private Integer courseItemId;

    private Integer courseNum;

    private BigDecimal period;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanCourseObjId() {
        return planCourseObjId;
    }

    public void setPlanCourseObjId(Integer planCourseObjId) {
        this.planCourseObjId = planCourseObjId;
    }

    public Integer getCourseItemId() {
        return courseItemId;
    }

    public void setCourseItemId(Integer courseItemId) {
        this.courseItemId = courseItemId;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }
}