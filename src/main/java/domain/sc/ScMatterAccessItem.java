package domain.sc;

import java.io.Serializable;
import java.util.Date;

public class ScMatterAccessItem implements Serializable {
    private Integer id;

    private Integer accessId;

    private Integer matterItemId;

    private Date realHandTime;

    private Date fillTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public Integer getMatterItemId() {
        return matterItemId;
    }

    public void setMatterItemId(Integer matterItemId) {
        this.matterItemId = matterItemId;
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