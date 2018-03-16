package persistence.common.bean;

import domain.cet.CetTrainView;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/3/14.
 */
public class ICetTrain extends CetTrainView {

    private Integer traineeId;

    private Integer courseCount;

    private BigDecimal totalPeriod;

    private BigDecimal finishPeriod;

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public BigDecimal getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(BigDecimal totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
    }
}
