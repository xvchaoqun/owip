package domain.cet;

import java.io.Serializable;
import java.math.BigDecimal;

public class CetTrainCourseObjResult implements Serializable {
    private Integer id;

    private Integer trainCourseObjId;

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

    public Integer getTrainCourseObjId() {
        return trainCourseObjId;
    }

    public void setTrainCourseObjId(Integer trainCourseObjId) {
        this.trainCourseObjId = trainCourseObjId;
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