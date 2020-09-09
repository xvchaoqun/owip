package domain.cet;

import sys.jackson.SignRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CetPlanCourseObj implements Serializable {
    private Integer id;

    private Integer planCourseId;

    private Integer objId;

    @SignRes
    private String note;

    private Integer num;

    private Boolean isFinished;

    private BigDecimal period;

    private Date chooseTime;

    private Integer chooseUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanCourseId() {
        return planCourseId;
    }

    public void setPlanCourseId(Integer planCourseId) {
        this.planCourseId = planCourseId;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public Date getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(Date chooseTime) {
        this.chooseTime = chooseTime;
    }

    public Integer getChooseUserId() {
        return chooseUserId;
    }

    public void setChooseUserId(Integer chooseUserId) {
        this.chooseUserId = chooseUserId;
    }
}