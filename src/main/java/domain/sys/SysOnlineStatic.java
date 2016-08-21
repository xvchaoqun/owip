package domain.sys;

import java.io.Serializable;
import java.util.Date;

public class SysOnlineStatic implements Serializable {
    private Integer id;

    private Integer onlineCount;

    private Integer bks;

    private Integer yjs;

    private Integer jzg;

    private Integer day;

    private Integer month;

    private Integer year;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getBks() {
        return bks;
    }

    public void setBks(Integer bks) {
        this.bks = bks;
    }

    public Integer getYjs() {
        return yjs;
    }

    public void setYjs(Integer yjs) {
        this.yjs = yjs;
    }

    public Integer getJzg() {
        return jzg;
    }

    public void setJzg(Integer jzg) {
        this.jzg = jzg;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}