package domain.pmd;

import java.io.Serializable;
import java.util.Date;

public class PmdNormValueLog implements Serializable {
    private Integer id;

    private Integer normValueId;

    private Date startTime;

    private Date endTime;

    private Integer startUserId;

    private Integer endUserId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNormValueId() {
        return normValueId;
    }

    public void setNormValueId(Integer normValueId) {
        this.normValueId = normValueId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Integer startUserId) {
        this.startUserId = startUserId;
    }

    public Integer getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(Integer endUserId) {
        this.endUserId = endUserId;
    }
}