package domain.train;

import java.io.Serializable;

public class TrainEvaResult implements Serializable {
    private Integer id;

    private Integer trainId;

    private Integer courseId;

    private Integer inspectorId;

    private Integer evaTableId;

    private Integer normId;

    private Integer rankId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Integer getEvaTableId() {
        return evaTableId;
    }

    public void setEvaTableId(Integer evaTableId) {
        this.evaTableId = evaTableId;
    }

    public Integer getNormId() {
        return normId;
    }

    public void setNormId(Integer normId) {
        this.normId = normId;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }
}