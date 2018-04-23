package persistence.cet.common;

public class StatTrainBean {

    public Integer trainCourseId;
    public Integer inspectorId;
    public Double totalScore;
    public String feedback;

    public Integer getTrainCourseId() {
        return trainCourseId;
    }

    public void setTrainCourseId(Integer trainCourseId) {
        this.trainCourseId = trainCourseId;
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
