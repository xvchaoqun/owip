package domain.cet;

import java.io.Serializable;
import java.math.BigDecimal;

public class CetTrainCourseStatView implements Serializable {
    private Integer trainCourseId;

    private Integer courseId;

    private BigDecimal score;

    private Integer trainId;

    private String name;

    private Integer selectedCount;

    private Integer finishCount;

    private Integer evaFinishCount;

    private static final long serialVersionUID = 1L;

    public Integer getTrainCourseId() {
        return trainCourseId;
    }

    public void setTrainCourseId(Integer trainCourseId) {
        this.trainCourseId = trainCourseId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Integer selectedCount) {
        this.selectedCount = selectedCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getEvaFinishCount() {
        return evaFinishCount;
    }

    public void setEvaFinishCount(Integer evaFinishCount) {
        this.evaFinishCount = evaFinishCount;
    }
}