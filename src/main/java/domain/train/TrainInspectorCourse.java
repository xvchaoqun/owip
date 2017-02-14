package domain.train;

import java.io.Serializable;
import java.util.Date;

public class TrainInspectorCourse implements Serializable {
    private Integer id;

    private Integer inspectorId;

    private Integer courseId;

    private String tempdata;

    private String feedback;

    private Integer score;

    private Date submitTime;

    private String submitIp;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTempdata() {
        return tempdata;
    }

    public void setTempdata(String tempdata) {
        this.tempdata = tempdata == null ? null : tempdata.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp == null ? null : submitIp.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}