package domain.sc;

import java.io.Serializable;
import java.util.Date;

public class ScMatterItem implements Serializable {
    private Integer id;

    private Integer matterId;

    private Integer userId;

    private Date realHandTime;

    private Date fillTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatterId() {
        return matterId;
    }

    public void setMatterId(Integer matterId) {
        this.matterId = matterId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getRealHandTime() {
        return realHandTime;
    }

    public void setRealHandTime(Date realHandTime) {
        this.realHandTime = realHandTime;
    }

    public Date getFillTime() {
        return fillTime;
    }

    public void setFillTime(Date fillTime) {
        this.fillTime = fillTime;
    }
}