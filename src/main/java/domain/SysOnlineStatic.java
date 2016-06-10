package domain;

import java.io.Serializable;
import java.util.Date;

public class SysOnlineStatic implements Serializable {
    private Integer id;

    private Integer count;

    private Integer bks;

    private Integer yjs;

    private Integer jzg;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}